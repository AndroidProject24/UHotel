package com.acuteksolutions.uhotel.data.service;

import com.acuteksolutions.uhotel.intdef.LinkDef;
import com.acuteksolutions.uhotel.intdef.ParseGsonDef;
import com.acuteksolutions.uhotel.intdef.PathDef;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.data.Category;
import com.acuteksolutions.uhotel.mvp.model.data.Product;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.utils.Constant;
import com.acuteksolutions.uhotel.utils.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by toan.it
 * Date: 25/05/2016
 */
@Singleton
public class RestData {
  private final RestApi mRestApi;
  @Inject
  public RestData(RestApi restApi) {
    mRestApi = restApi;
  }

  //Login
  public Observable<Login> getLogin(String pass) {
    return mRestApi.getLogin(Constant.DEVICE_MAC,pass,"default","default")
            .subscribeOn(Schedulers.io())
            .map(data->data.result)
            .observeOn(AndroidSchedulers.mainThread());
  }

  //Movies
  public Observable<List<Category>> getCategory(int regionUID) {
    return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_LIST_CATEGORY.replace(PathDef.REGION_UID,String.valueOf(regionUID)))
            .subscribeOn(Schedulers.io())
            .map(stringJsonString -> {
              List<Category> categoryList =null;
              try {
                JSONObject result = new JSONObject(stringJsonString.result);
                JSONArray list=result.getJSONArray(ParseGsonDef.ARRAY);
                categoryList = new Gson().fromJson(list.toString(),new TypeToken<List<Category>>(){}.getType());
              }catch (JSONException e){
                e.printStackTrace();
              }
              return categoryList;
            })
            .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<List<VODInfo>> getListMovies(String idList) {
    return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_LIST_MOVIES.replace(PathDef.LIST_ID,String.valueOf(idList)))
            .map(stringJsonString -> {
              List<VODInfo> vodInfoList =null;
              try {
                JSONObject result = new JSONObject(stringJsonString.result);
                Logger.e(result.toString());
                JSONArray list=result.getJSONArray(ParseGsonDef.ARRAY);
                Logger.e(list.toString());
                vodInfoList = new Gson().fromJson(list.toString(),new TypeToken<List<Category>>(){}.getType());
              }catch (JSONException e){
                e.printStackTrace();
              }
              return vodInfoList;
            });
  }

  public Observable<List<VODInfo>> getMoviesDetails(String regionUID,String catID) {
    return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_MOVIES_DETAILS.replace(PathDef.REGION_UID,regionUID).replace(PathDef.CAT_ID,catID))
            .subscribeOn(Schedulers.io())
            .map(stringJsonString -> {
              List<Product> productList = new ArrayList<>();
              List<String> purchasesItem = new ArrayList<>();
              try {
                long timestart=System.currentTimeMillis();
                /*JsonParser jsonParser = new JsonParser();
                JsonElement jsonTree = jsonParser.parse(stringJsonString.result);
                if(jsonTree.isJsonObject()) {
                  JsonObject jsonObject = jsonTree.getAsJsonObject();
                  JsonArray list=jsonObject.getAsJsonArray(ParseGsonDef.ARRAY);
                  if(list.isJsonArray()) {
                    for (int i = 0; i < list.size(); i++) {
                      JsonElement items = list.get(i);
                      JsonObject object=items.getAsJsonObject();
                      JsonArray item = object.getAsJsonArray(ParseGsonDef.ITEMS);
                      for (int k = 0; k < item.size(); k++) {
                        JsonElement id = list.get(k);
                        JsonObject objectID=id.getAsJsonObject();
                        if (null != objectID) {
                          purchasesItem.add(objectID.get(ParseGsonDef.ID).getAsString());
                        }
                      }
                      Product product = new Product(purchasesItem, null);
                      productList.add(product);
                      Logger.e(productList.toString());
                    }
                  }
                }
                //TEST Time
                long timestart=System.currentTimeMillis();*/
                JSONObject result = new JSONObject(stringJsonString.result);
                JSONArray list=result.getJSONArray(ParseGsonDef.ARRAY);
                for (int i = 0; i < list.length(); i++) {
                  JSONObject items = list.getJSONObject(i);
                  JSONArray item=items.getJSONArray(ParseGsonDef.ITEMS);
                  for (int k = 0; k < item.length(); k++) {
                    JSONObject id = item.getJSONObject(k);
                    if (null != id) {
                      purchasesItem.add(id.getString(ParseGsonDef.ID));
                    }
                  }
                  Product product = new Product(purchasesItem, null);
                  productList.add(product);
                  Logger.e(productList.toString());
                }
                Logger.e("Time="+(System.currentTimeMillis()-timestart));
              }catch (JSONException e){
                e.printStackTrace();
              }
              return Preconditions.checkNotNull(productList).get(0).getItems();
            })
            .flatMap(new Func1<List<String>, Observable<List<VODInfo>>>() {
              @Override
              public Observable<List<VODInfo>> call(List<String> strings) {
                StringBuilder builder = new StringBuilder();
                for (String s : strings) {
                  if (builder.length() == 0) builder.append(s);
                  builder.append(",").append(s);
                }
                Logger.e(builder.toString()+"\n substring="+strings.toString().substring(1,strings.toString().length()-1));
                return getListMovies(builder.toString());
              }
            })
            .observeOn(AndroidSchedulers.mainThread());
  }
}
