package com.acuteksolutions.uhotel.data.service;

import com.acuteksolutions.uhotel.annotation.LinkDef;
import com.acuteksolutions.uhotel.annotation.ParseGsonDef;
import com.acuteksolutions.uhotel.annotation.PathDef;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.JsonString;
import com.acuteksolutions.uhotel.mvp.model.data.Category;
import com.acuteksolutions.uhotel.mvp.model.data.Detail;
import com.acuteksolutions.uhotel.mvp.model.data.Product;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.utils.Constant;
import com.acuteksolutions.uhotel.utils.Preconditions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
  private final PreferencesHelper mPreferencesHelper;
  @Inject
  public RestData(RestApi restApi,PreferencesHelper preferencesHelper) {
    this.mRestApi = restApi;
    this.mPreferencesHelper=preferencesHelper;
  }

  //Login
  public Observable<Login> getLogin(String pass) {
    return mRestApi.getLogin(Constant.DEVICE_MAC,pass,"default","default")
        .subscribeOn(Schedulers.io())
        .map(data->data.result)
        .observeOn(AndroidSchedulers.mainThread());
  }

  //Movies
  public Observable<List<Category>> getCategory() {
    return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_LIST_CATEGORY.replace(PathDef.REGION_UID,String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid())))
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
        .subscribeOn(Schedulers.io())
        .map(stringJsonString -> {
          List<VODInfo> vodInfoList = new ArrayList<>();
          try {
            JSONArray result = new JSONArray(stringJsonString.result);
            for(int i=0;i<result.length();i++){
              JSONObject item = result.getJSONObject(i);
              JSONObject jsonDetail = item.getJSONObject("details");
              JSONArray jsonGenres = jsonDetail.getJSONArray("genres");
              List<String> genres = new ArrayList<>();
              for (int j = 0; j < jsonGenres.length(); j++) {
                genres.add(jsonGenres.getString(j));
              }
              Detail detail = new Detail(
                  (jsonDetail.optString("title").equals("")?jsonDetail.getString("title"): "N/A"),
                  (jsonDetail.optString("actors").equals("")? jsonDetail.getString("actors") : "N/A"),
                  (jsonDetail.optString("director").equals("") ? jsonDetail.getString("director") : "N/A"),
                  jsonDetail.optInt("duration",0),
                  LinkDef.LINK_IMAGE_URL.replace("{regionId}", String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())) + jsonDetail.getString("poster"),
                  (jsonDetail.optString("description").equals("") ? jsonDetail.getString("description"): "No description"),
                  genres
              );
              VODInfo vod = new VODInfo(
                  item.getInt("purchaseId"),
                  item.getString("contentInfoId"),
                  detail,
                  item.getInt("contentId")
              );
              vodInfoList.add(vod);
            }
          }catch (JSONException e){
            e.printStackTrace();
          }
          return vodInfoList;
        });
  }

  public Observable<List<VODInfo>> getMoviesDetails(String catID) {
    return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_MOVIES_DETAILS.replace(PathDef.REGION_UID, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid())).replace(PathDef.CAT_ID,catID))
        .subscribeOn(Schedulers.io())
        .map(stringJsonString -> {
          List<Product> productList = new ArrayList<>();
          List<String> purchasesItem = new ArrayList<>();
          try {
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
              //Logger.e(productList.toString());
            }
          }catch (JSONException e){
            e.printStackTrace();
          }
          return Preconditions.checkNotNull(productList).get(0).getItems();
        })
        .flatMap(strings -> {
          StringBuilder builder = new StringBuilder();
          for (String s : strings) {
            if (builder.length() == 0) builder.append(s);
            builder.append(",").append(s);
          }
          return getListMovies(builder.toString());
        })
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<List<Channel>> getAllChannel() {
    return mRestApi.getChannel(Constant.DEVICE_MAC, LinkDef.LINK_LIVE_ALL_CHANNEL.replace(PathDef.REGION_UID, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())))
        .subscribeOn(Schedulers.io())
        .map(new Func1<JsonString<String>, List<Channel>>() {
          @Override public List<Channel> call(JsonString<String> stringJsonString) {
            return null;
          }
        })
        .observeOn(AndroidSchedulers.mainThread());
  }

  /*PIN*/
  public Observable<Boolean> verifyPin(String pin) {
    return mRestApi.verifyPin(Constant.DEVICE_MAC,  String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid()),"authorize",pin)
        .subscribeOn(Schedulers.io())
        .map(new Func1<JsonString<Boolean>, Boolean>() {
          @Override public Boolean call(JsonString<Boolean> booleanJsonString) {
            try {
              Logger.e("booleanJsonString="+booleanJsonString.toString()+"result="+booleanJsonString.result);
             /* final JSONObject resp = new JSONObject(response.body().string());
              if (!resp.isNull("type")) {
                if ("EXCEPTION".equals(resp.getString("type"))) {
                  return false;
                } else {
                  return resp.getBoolean(booleanJsonString.result);
                }
              }*/
            }catch (Exception e){
              e.printStackTrace();
            }
            return false;
          }
        })
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<Boolean> changePin(String newPin,String oldPin) {
    return mRestApi.changePin(Constant.DEVICE_MAC,  String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid()), newPin, oldPin)
        .subscribeOn(Schedulers.io())
        .map(new Func1<JsonString<Boolean>, Boolean>() {
          @Override public Boolean call(JsonString<Boolean> booleanJsonString) {
            try {
              Logger.e("booleanJsonString="+booleanJsonString.toString()+"result="+booleanJsonString.result);
             /* final JSONObject resp = new JSONObject(response.body().string());
              if (!resp.isNull("type")) {
                if ("EXCEPTION".equals(resp.getString("type"))) {
                  return false;
                } else {
                  return resp.getBoolean(booleanJsonString.result);
                }
              }*/
            }catch (Exception e){
              e.printStackTrace();
            }
            return false;
          }
        })
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<Boolean> saveSetting(String data,String pin) {
    return mRestApi.saveSetting(Constant.DEVICE_MAC,  String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid()),data,pin)
        .subscribeOn(Schedulers.io())
        .map(new Func1<JsonString<Boolean>, Boolean>() {
          @Override public Boolean call(JsonString<Boolean> booleanJsonString) {
            try {
              Logger.e("booleanJsonString="+booleanJsonString.toString()+"result="+booleanJsonString.result);
             /* final JSONObject resp = new JSONObject(response.body().string());
              if (!resp.isNull("type")) {
                if ("EXCEPTION".equals(resp.getString("type"))) {
                  return false;
                } else {
                  return resp.getBoolean(booleanJsonString.result);
                }
              }*/
            }catch (Exception e){
              e.printStackTrace();
            }
            return false;
          }
        })
        .observeOn(AndroidSchedulers.mainThread());
  }
}
