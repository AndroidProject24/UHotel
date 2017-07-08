package com.acuteksolutions.uhotel.data.repository;

import com.acuteksolutions.uhotel.annotation.LinkDef;
import com.acuteksolutions.uhotel.annotation.ParseGsonDef;
import com.acuteksolutions.uhotel.annotation.PathDef;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.local.RealmManager;
import com.acuteksolutions.uhotel.data.service.RestApi;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.livetv.Program;
import com.acuteksolutions.uhotel.mvp.model.livetv.Stream;
import com.acuteksolutions.uhotel.mvp.model.livetv.TVInfo;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.Detail;
import com.acuteksolutions.uhotel.mvp.model.movies.DetailsItem;
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
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;

/**
 * Created by Toan.IT on 5/14/17.
 * Email: huynhvantoan.itc@gmail.com
 */
@Singleton
public class Repository implements DataSource {
    private final RestApi mRestApi;
    private final RealmManager mRealmManager;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public Repository(RestApi restApi, RealmManager realmManager, PreferencesHelper preferencesHelper) {
        this.mRestApi = restApi;
        this.mRealmManager = realmManager;
        this.mPreferencesHelper = preferencesHelper;
    }

    //Login
    public Observable<Login> getLogin(String pass) {
        return mRestApi.getLogin(Constant.DEVICE_MAC, pass, "default", "default")
                .subscribeOn(Schedulers.io())
                .map(data -> data.result)
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*PIN*/
    public Observable<Boolean> verifyPin(String pin) {
        return mRestApi.verifyPin(Constant.DEVICE_MAC, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid()), pin)
                .subscribeOn(Schedulers.io())
                .map(booleanJsonString -> Boolean.valueOf(booleanJsonString.result))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> changePin(String newPin, String oldPin) {
        return mRestApi.changePin(Constant.DEVICE_MAC, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid()), newPin, oldPin)
                .subscribeOn(Schedulers.io())
                .map(booleanJsonString -> Boolean.valueOf(booleanJsonString.result))
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> saveSetting(String data, String pin) {
        return mRestApi.saveSetting(Constant.DEVICE_MAC, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getProfileUid()), data, pin)
                .subscribeOn(Schedulers.io())
                .map(booleanJsonString -> Boolean.valueOf(booleanJsonString.result))
                .observeOn(AndroidSchedulers.mainThread());
    }

    /*GETLINK*/
    public Observable<String> getLinkStream(String cid) {
        return mRestApi.getLinkStream(Constant.DEVICE_MAC, LinkDef.LINK_STREAM.replace(PathDef.REGION_UID, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())).replace(PathDef.CID, cid))
                .subscribeOn(Schedulers.io())
                .map(stringJsonString -> {
                    String linkStream = "";
                    try {
                        JSONObject result = new JSONObject(stringJsonString.result);
                        JSONArray list = result.getJSONArray(ParseGsonDef.ARRAY);
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject items = list.getJSONObject(i);
                            JSONArray item = items.getJSONArray(ParseGsonDef.MEDIA_RESOURCES);
                            List<Stream> streamList =
                                    new Gson().fromJson(item.toString(), new TypeToken<List<Stream>>() {
                                    }.getType());
                            Logger.e(streamList.toString());
                            if(Preconditions.checkList(streamList))
                               linkStream = streamList.get(0).getSrc();
                            else
                                linkStream="";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return linkStream;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    //Movies
    private Observable<List<Category>> getCloudCategory() {
        return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_LIST_CATEGORY.replace(PathDef.REGION_UID, String.valueOf(
                Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())))
                .subscribeOn(Schedulers.io())
                .flatMap(stringJsonString -> {
                    List<Category> categoryList = new ArrayList<>();
                    try {
                        JSONObject result = new JSONObject(stringJsonString.result);
                        JSONArray list = result.getJSONArray(ParseGsonDef.ARRAY);
                        categoryList = new Gson().fromJson(list.toString(), new TypeToken<List<Category>>() {
                        }.getType());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (categoryList != null)
                        return Observable.just(categoryList);
                    return Observable.empty();
                })
                .subscribeOn(Schedulers.computation())
                .doOnNext(mRealmManager::saveListCategory)
                .doOnError(Throwable::printStackTrace);
    }

    private Observable<String> getCloudListIDMovies(String categoryID) {
        return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_MOVIES_DETAILS.replace(PathDef.REGION_UID, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())).replace(PathDef.CAT_ID, categoryID))
                .subscribeOn(Schedulers.io())
                .flatMap(stringJsonString -> {
                    RealmList<Product> productList = new RealmList<>();
                    RealmList<Item> purchasesItem = new RealmList<>();
                    try {
                        JSONObject result = new JSONObject(stringJsonString.result);
                        JSONArray list = result.getJSONArray(ParseGsonDef.ARRAY);
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject items = list.getJSONObject(i);
                            JSONArray item = items.getJSONArray(ParseGsonDef.ITEMS);
                            for (int k = 0; k < item.length(); k++) {
                                JSONObject id = item.getJSONObject(k);
                                if (null != id) {
                                    purchasesItem.add(new Item(id.getString(ParseGsonDef.ID)));
                                }
                            }
                            Product product = new Product(purchasesItem, null);
                            productList.add(product);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return Observable.just(productList);
                })
                .observeOn(Schedulers.computation())
                .doOnNext(products -> mRealmManager.saveListMoviesFromCategory(products, categoryID))
                .map(products -> {
                    StringBuilder builder = new StringBuilder();
                    for (Item item : products.get(0).getItems()) {
                        if (builder.length() == 0) builder.append(item.getItem());
                        builder.append(",").append(item.getItem());
                    }
                    return builder.toString();
                })
                .doOnError(Throwable::printStackTrace);
    }

    private Observable<List<VODInfo>> getCloudMoviesFromCategory(String idList, String categoryID) {
        //Logger.e("getCloudMoviesFromCategory=" + "\n idList=" + idList + "\n categoryID=" + categoryID);
        return mRestApi.getPathMovies(Constant.DEVICE_MAC, LinkDef.LINK_LIST_MOVIES.replace(PathDef.LIST_ID, String.valueOf(idList)))
                .subscribeOn(Schedulers.io())
                .flatMap(stringJsonString -> {
                    RealmList<VODInfo> vodInfoList = new RealmList<>();
                    try {
                        JSONArray result = new JSONArray(stringJsonString.result);
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject item = result.getJSONObject(i);
                            JSONObject jsonDetail = item.getJSONObject("details");
                            JSONArray jsonGenres = jsonDetail.getJSONArray("genres");
                            RealmList<DetailsItem> genres = new RealmList<>();
                            for (int j = 0; j < jsonGenres.length(); j++) {
                                genres.add(new DetailsItem(jsonGenres.getString(j)));
                            }
                            Detail detail = new Detail(
                                    (!jsonDetail.optString("title").equals("") ? jsonDetail.getString("title") : "N/A"),
                                    (!jsonDetail.optString("actors").equals("") ? jsonDetail.getString("actors") : "N/A"),
                                    (!jsonDetail.optString("director").equals("") ? jsonDetail.getString("director") : "N/A"),
                                    jsonDetail.optInt("duration", 0),
                                    LinkDef.LINK_IMAGE_URL.replace("regionId", String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())) + jsonDetail.getString("poster"),
                                    (!jsonDetail.optString("description").equals("") ? jsonDetail.getString("description") : "No description"),
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
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return Observable.just(vodInfoList);
                })
                .observeOn(Schedulers.computation())
                .doOnNext(vodInfoList -> mRealmManager.saveListMovies(vodInfoList, categoryID))
                .map((Function<RealmList<VODInfo>, List<VODInfo>>) vodInfos -> vodInfos)
                .doOnError(Throwable::printStackTrace);
    }


    /*LiveTV*/
    private Observable<List<Channel>> getCloudAllChannel() {
        return mRestApi.getChannel(Constant.DEVICE_MAC, LinkDef.LINK_LIVE_ALL_CHANNEL.replace(PathDef.REGION_UID, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())))
                .subscribeOn(Schedulers.io())
                .flatMap(stringJsonString -> {
                    List<Channel> channelList;
                    try {
                        JSONObject result = new JSONObject(stringJsonString.result);
                        JSONArray list = result.getJSONArray(ParseGsonDef.ARRAY);
                        channelList = new Gson().fromJson(list.toString(), new TypeToken<List<Channel>>() {
                        }.getType());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null;
                    }
                    return Observable.just(channelList);
                })
                .subscribeOn(Schedulers.computation())
                .doOnNext(mRealmManager::saveAllChannel)
                .doOnError(Throwable::printStackTrace);
    }

    private Observable<List<TVInfo>> getCloudProgram(List<Channel> channelList, Date currentDate) {
        return mRestApi.getTVProgram(Constant.DEVICE_MAC, LinkDef.LINK_LIVE_PROGRAM_BY_ID_PATH
                .replace(PathDef.REGION_UID, String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId()))
                .replace(PathDef.DATE, Utils.parseDate(currentDate, "yyyy-MM-dd")))
                .subscribeOn(Schedulers.io())
                .flatMap(stringJsonString -> {
                    List<Program> programList;
                    try {
                        JSONObject result = new JSONObject(stringJsonString.result);
                        JSONArray list = result.getJSONArray(ParseGsonDef.ARRAY);
                        programList = new Gson().fromJson(list.toString(), new TypeToken<List<Program>>() {
                        }.getType());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null;
                    }
                    return Observable.just(programList);
                })
                .subscribeOn(Schedulers.computation())
                .doOnNext(mRealmManager::saveListProgram)
                .flatMap(programs -> {
                    List<TVInfo> listTV = new ArrayList<>();
                    listTV = mRealmManager.setListNowUp(listTV, channelList, programs);
                    if (Preconditions.checkList(listTV))
                        return Observable.just(listTV);
                    return Observable.empty();
                })
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Observable<List<Category>> getCategory() {
        return Observable.concat(mRealmManager.getCategory(), getCloudCategory())
                .filter(Preconditions::checkList)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<VODInfo>> getListMovies(String categoryID) {
        return Observable.concat(mRealmManager.getListIDMovies(categoryID),
                getCloudListIDMovies(categoryID))
                .flatMap(idList -> getMoviesFromCategory(idList, categoryID))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<VODInfo>> getMoviesFromCategory(String idList, String categoryID) {
        if (!Preconditions.isEmpty(idList))
            return Observable.concat(mRealmManager.getMoviesFromCategory(idList, categoryID),
                    getCloudMoviesFromCategory(idList, categoryID));
        return Observable.empty();
    }

    @Override
    public Observable<List<Channel>> getAllChannel() {
        return Observable.concat(mRealmManager.getAllChannel(), getCloudAllChannel())
                .filter(Preconditions::checkList)
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<TVInfo>> getProgram(List<Channel> channelList, Date currentDate) {
        if (Preconditions.checkList(channelList))
            return Observable.concat(mRealmManager.getProgram(channelList, currentDate),
                    getCloudProgram(channelList, currentDate))
                    .filter(Preconditions::checkList)
                    .observeOn(AndroidSchedulers.mainThread());
        return Observable.empty();
    }

    @Override
    public void clear() {
        mRealmManager.clear();
    }
}
