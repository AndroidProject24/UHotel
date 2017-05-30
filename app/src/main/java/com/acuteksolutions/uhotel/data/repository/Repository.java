package com.acuteksolutions.uhotel.data.repository;

import com.acuteksolutions.uhotel.annotation.LinkDef;
import com.acuteksolutions.uhotel.annotation.ParseGsonDef;
import com.acuteksolutions.uhotel.annotation.PathDef;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.local.RealmManager;
import com.acuteksolutions.uhotel.data.service.RestApi;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.JsonString;
import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.livetv.Program;
import com.acuteksolutions.uhotel.mvp.model.livetv.Stream;
import com.acuteksolutions.uhotel.mvp.model.livetv.TVInfo;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.Detail;
import com.acuteksolutions.uhotel.mvp.model.movies.Item;
import com.acuteksolutions.uhotel.mvp.model.movies.Product;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;
import com.acuteksolutions.uhotel.utils.Constant;
import com.acuteksolutions.uhotel.utils.Preconditions;
import com.acuteksolutions.uhotel.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.RealmList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Toan.IT on 5/14/17.
 * Email: huynhvantoan.itc@gmail.com
 */
@Singleton
public class Repository implements DataSource{
    private final RestApi mRestApi;
    private final RealmManager mRealmManager;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public Repository(RestApi restApi,RealmManager realmManager,PreferencesHelper preferencesHelper) {
        this.mRestApi = restApi;
        this.mRealmManager=realmManager;
        this.mPreferencesHelper=preferencesHelper;
    }

    //Login
    public Observable<Login> getLogin(String pass) {
        return mRestApi.getLogin(Constant.DEVICE_MAC,pass,"default","default")
                .subscribeOn(Schedulers.io())
                .map(data->data.result)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*LiveTV*/
    public Observable<List<Channel>> getAllChannel() {
        return mRestApi.getChannel(Constant.DEVICE_MAC, LinkDef.LINK_LIVE_ALL_CHANNEL.replace(PathDef.REGION_UID, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())))
                .subscribeOn(Schedulers.io())
                .map(stringJsonString -> {
                    List<Channel> channelList = null;
         /* List<Stream> channelStreamList=null;
          try {
            JSONObject obj = new JSONObject(stringJsonString.result);
            JSONArray jsonArray = obj.optJSONArray("list");
            channelStreamList = new ArrayList<>(jsonArray.length());
            for (int k = 0; k < jsonArray.length(); k++) {
              JSONObject jsonChannel = jsonArray.getJSONObject(k);
              if (jsonChannel != null) {
                JSONArray jsonStreams = jsonChannel.getJSONArray("streams");
                if (jsonStreams.length() == 0) {
                  channelStreamList.add(new Stream());
                  continue;
                }
                final JSONObject jsonStream = jsonStreams.getJSONObject(0);
                final Stream stream = new Stream(
                    jsonStream.getString("src"),
                    jsonStream.getString("provider"),
                    jsonStream.getString("protocolStack"),
                    jsonStream.getString("profiles"),
                    jsonStream.getString("capabilities")
                );
                if (!channelStreamList.contains(stream)) channelStreamList.add(stream);
              }
            }*/
                    try{
                        JSONObject result = new JSONObject(stringJsonString.result);
                        JSONArray list=result.getJSONArray(ParseGsonDef.ARRAY);
                        channelList=new Gson().fromJson(list.toString(),new TypeToken<List<Channel>>(){}.getType());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null;
                    }
                    return channelList;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<TVInfo>> getProgram(List<Channel> channelList,Date currentDate) {
        return mRestApi.getTVProgram(Constant.DEVICE_MAC, LinkDef.LINK_LIVE_PROGRAM_BY_ID_PATH
                .replace(PathDef.REGION_UID,String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getLanguageId()))
                .replace(PathDef.DATE, Utils.parseDate(currentDate, "yyyy-MM-dd")))
                .subscribeOn(Schedulers.io())
                .map(stringJsonString -> {
                    List<TVInfo> listTV=new ArrayList<>();
                    try {
                        JSONObject result = new JSONObject(stringJsonString.result);
                        JSONArray list=result.getJSONArray(ParseGsonDef.ARRAY);
                        List<Program> programList = new Gson().fromJson(list.toString(),new TypeToken<List<Program>>(){}.getType());
                        listTV=setListNowUp(listTV,channelList,programList);
                    }catch (JSONException e){
                        e.printStackTrace();
                        return null;
                    }
                    return listTV;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<TVInfo> setListNowUp(List<TVInfo> listTV,List<Channel> channelInfoList, List<Program> programInfoList) {
        TVInfo tvInfo;
        for (Program programInfo : programInfoList) {
            tvInfo = new TVInfo();
            tvInfo.setDescription(programInfo.getDescription());
            tvInfo.setTitle(programInfo.getTitle());
            tvInfo.setStart(programInfo.getStart());
            tvInfo.setEnd(programInfo.getEnd());
            Channel channelInfo = getChannelInfoOnId(channelInfoList, programInfo.getIdChannel());
            if (channelInfo != null) {
                tvInfo.setChannelStreams(channelInfo.getStreams());
                tvInfo.setChannelName(channelInfo.getName());
                tvInfo.setPictureLink(channelInfo.getIcon());
                tvInfo.setChannelNo(channelInfo.getNumber());
            }
            listTV.add(tvInfo);
        }
        Collections.sort(listTV,new TVInfo());
        return listTV;
    }

    private Channel getChannelInfoOnId(List<Channel> channelInfoList, long channelId) {
        for (Channel channelInfo : channelInfoList) {
            if (channelInfo.getId() == channelId)
                return channelInfo;
        }
        return null;
    }

    /*PIN*/
    public Observable<Boolean> verifyPin(String pin) {
        return mRestApi.verifyPin(Constant.DEVICE_MAC,  String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid()),pin)
                .subscribeOn(Schedulers.io())
                .map(booleanJsonString -> Boolean.valueOf(booleanJsonString.result))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> changePin(String newPin,String oldPin) {
        return mRestApi.changePin(Constant.DEVICE_MAC,  String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid()), newPin, oldPin)
                .subscribeOn(Schedulers.io())
                .map(booleanJsonString -> Boolean.valueOf(booleanJsonString.result))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> saveSetting(String data,String pin) {
        return mRestApi.saveSetting(Constant.DEVICE_MAC,  String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid()),data,pin)
                .subscribeOn(Schedulers.io())
                .map(booleanJsonString -> Boolean.valueOf(booleanJsonString.result))
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*GETLINK*/
    public Observable<String> getLinkStream(String cid) {
        return mRestApi.getLinkStream(Constant.DEVICE_MAC, LinkDef.LINK_STREAM.replace(PathDef.REGION_UID,String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())).replace(PathDef.CID,cid))
                .subscribeOn(Schedulers.io())
                .map(stringJsonString -> {
                    String linkStream ="";
                    try {
                        JSONObject result = new JSONObject(stringJsonString.result);
                        JSONArray list=result.getJSONArray(ParseGsonDef.ARRAY);
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject items = list.getJSONObject(i);
                            JSONArray item = items.getJSONArray(ParseGsonDef.MEDIA_RESOURCES);
                            List<Stream> streamList =
                                    new Gson().fromJson(item.toString(), new TypeToken<List<Stream>>() {
                                    }.getType());
                            Logger.e(streamList.toString());
                            linkStream=streamList.get(0).getSrc();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    return linkStream;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    //Movies
    private Observable<List<Category>> getCloudCategory() {
        return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_LIST_CATEGORY.replace(PathDef.REGION_UID,String.valueOf(
                Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())))
                .subscribeOn(Schedulers.io())
                .doOnNext(stringJsonString -> {
                    List<Category> categoryList =null;
                    try {
                        JSONObject result = new JSONObject(stringJsonString.result);
                        JSONArray list=result.getJSONArray(ParseGsonDef.ARRAY);
                        categoryList = new Gson().fromJson(list.toString(),new TypeToken<List<Category>>(){}.getType());
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    mRealmManager.saveListCategory(categoryList);
                })
                .flatMap((Func1<JsonString<String>, Observable<List<Category>>>) stringJsonString -> Observable.just(null))
                .doOnError(Throwable::printStackTrace);
    }


    private Observable<String> getCloudMoviesDetails(String categoryID) {
       /* return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_MOVIES_DETAILS.replace(PathDef.REGION_UID, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())).replace(PathDef.CAT_ID,catID))
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
                        return null;
                    }
                    return Preconditions.checkNotNull(productList).get(0).getItems();
                })
                .flatMap(strings -> {
                    StringBuilder builder = new StringBuilder();
                    for (String s : strings) {
                        if (builder.length() == 0) builder.append(s);
                        builder.append(",").append(s);
                    }
                    return getCloudListMovies(builder.toString());
                })
                .observeOn(AndroidSchedulers.mainThread());*/
       return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_MOVIES_DETAILS.replace(PathDef.REGION_UID, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())).replace(PathDef.CAT_ID,categoryID))
               .subscribeOn(Schedulers.io())
               .doOnNext(stringJsonString -> {
                   RealmList<Product> productList = new RealmList<>();
                   RealmList<Item> purchasesItem = new RealmList<>();
                   try {
                       JSONObject result = new JSONObject(stringJsonString.result);
                       JSONArray list=result.getJSONArray(ParseGsonDef.ARRAY);
                       for (int i = 0; i < list.length(); i++) {
                           JSONObject items = list.getJSONObject(i);
                           JSONArray item=items.getJSONArray(ParseGsonDef.ITEMS);
                           for (int k = 0; k < item.length(); k++) {
                               JSONObject id = item.getJSONObject(k);
                               if (null != id) {
                                   purchasesItem.add(new Item(id.getString(ParseGsonDef.ID)));
                               }
                           }
                           Product product = new Product(i,purchasesItem, null);
                           productList.add(product);
                           mRealmManager.saveListMoviesDetails(productList,categoryID);
                       }
                   }catch (JSONException e){
                       e.printStackTrace();
                   }
               })
               .flatMap((Func1<JsonString<String>, Observable<String>>) stringJsonString -> Observable.just(null))
               .doOnError(Throwable::printStackTrace);
    }

    private Observable<List<VODInfo>> getCloudListMovies(String idList,String categoryID) {
        Logger.e(idList);
        return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_LIST_MOVIES.replace(PathDef.LIST_ID,String.valueOf(idList)))
                .subscribeOn(Schedulers.io())
                .doOnNext(stringJsonString -> {
                    RealmList<VODInfo> vodInfoList = new RealmList<>();
                    try {
                        Logger.e(stringJsonString.result);
                        JSONArray result = new JSONArray(stringJsonString.result);
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject item = result.getJSONObject(i);
                            JSONObject jsonDetail = item.getJSONObject("details");
                            JSONArray jsonGenres = jsonDetail.getJSONArray("genres");
                            RealmList<Item> genres = new RealmList<>();
                            for (int j = 0; j < jsonGenres.length(); j++) {
                                genres.add(new Item(jsonGenres.getString(j)));
                            }
                            Detail detail = new Detail(
                                    (jsonDetail.optString("title").equals("") ? jsonDetail.getString("title") : "N/A"),
                                    (jsonDetail.optString("actors").equals("") ? jsonDetail.getString("actors") : "N/A"),
                                    (jsonDetail.optString("director").equals("") ? jsonDetail.getString("director") : "N/A"),
                                    jsonDetail.optInt("duration", 0),
                                    LinkDef.LINK_IMAGE_URL.replace("regionId", String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())) + jsonDetail.getString("poster"),
                                    (jsonDetail.optString("description").equals("") ? jsonDetail.getString("description") : "No description"),
                                    genres
                            );
                            VODInfo vod = new VODInfo(
                                    item.getInt("purchaseId"),
                                    item.getString("contentInfoId"),
                                    detail,
                                    item.getInt("contentId"),
                                    categoryID
                            );
                            vodInfoList.add(vod);
                            mRealmManager.saveListMovies(vodInfoList,categoryID);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .flatMap((Func1<JsonString<String>, Observable<List<VODInfo>>>) stringJsonString -> Observable.just(null))
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Observable<List<Category>> getCategory() {
        Logger.e("getCategory=");
        return Observable.concat(getCloudCategory(),mRealmManager.getCategory())
                .map(list -> {
                    Logger.e("getCategory="+list.toString());
                    return list;
                })
                .filter(data -> !data.isEmpty())
                .first()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<VODInfo>> getListMovies(String idList,String categoryID) {
        Logger.e("getListMovies="+categoryID);
        return Observable.concat(getCloudListMovies(idList,categoryID),mRealmManager.getListMovies(idList,categoryID))
                .map(list -> {
                    Logger.e("getListMovies="+list.toString());
                    return list;
                })
                .filter(data -> !data.isEmpty())
                .first();
    }

    @Override
    public Observable<List<VODInfo>> getMoviesDetails(String categoryID) {
        Logger.e("getMoviesDetails="+categoryID);
        return Observable.concat(getCloudMoviesDetails(categoryID),mRealmManager.getMoviesProduct(categoryID))
                .flatMap(idList -> getListMovies(idList,categoryID))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
