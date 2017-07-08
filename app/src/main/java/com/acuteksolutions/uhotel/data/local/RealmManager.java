package com.acuteksolutions.uhotel.data.local;

import com.acuteksolutions.uhotel.data.repository.DataSource;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.livetv.Program;
import com.acuteksolutions.uhotel.mvp.model.livetv.TVInfo;
import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.Item;
import com.acuteksolutions.uhotel.mvp.model.movies.Product;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;
import com.acuteksolutions.uhotel.utils.Preconditions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmSchema;


public class RealmManager implements DataSource {
    public RealmConfiguration mRealmConfigDB;

    @Inject
    public RealmManager() {
        mRealmConfigDB = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(mRealmConfigDB);
    }

    public Realm getRealmDB() {
        return Realm.getDefaultInstance();
    }

    private RealmMigration migrationDB = (realm, oldVersion, newVersion) -> {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {

            oldVersion++;
        }


        if (oldVersion == 1) {

        }
    };

    public <T extends RealmObject> List<T> findAll(Class<T> clazz) {
        Realm realm = null;
        try {
            realm = getRealmDB();
            return realm.copyFromRealm(realm.where(clazz).findAll());
        } finally {
            if (realm != null)
                realm.close();
        }
    }

    //TODO: @Deprecated RXJAVA 1
   /* private <T extends RealmObject> Observable<RealmResults<T>> getListObjectAsync(Class<T> classObject) {
        return getRealmDB().where(classObject)
                .findAll()
                .asObservable();
    }

    private <T extends RealmObject> Observable<RealmResults<T>> getListObjectAsyncWithKey(Class<T> classObject, String key, String id) {
        return getRealmDB().where(classObject)
                .equalTo(key, id)
                .findAll()
                .asObservable();
    }

    private <T extends RealmObject> Observable<T> getObjectAsyncWithKey(Class<T> classObject, String key, String id) {
        return getRealmDB().where(classObject)
                .equalTo(key, id)
                .findFirst()
                .asObservable();
    }*/

    public <T extends RealmObject> List<T> findAllWithKey(Class<T> clazz, String categoryID) {
        Realm realm = null;
        try {
            realm = getRealmDB();
            return realm.copyFromRealm(realm.where(clazz).equalTo("id", categoryID).findAll());
        } finally {
            if (realm != null)
                realm.close();
        }
    }

    public <T extends RealmObject> T findFist(Class<T> clazz) {
        Realm realm = null;
        try {
            realm = getRealmDB();
            return realm.copyFromRealm(realm.where(clazz).findFirst());
        } finally {
            if (realm != null)
                realm.close();
        }
    }

    public <T extends RealmObject> T findFistWithKey(Class<T> clazz) {
        Realm realm = null;
        try {
            realm = getRealmDB();
            return realm.copyFromRealm(realm.where(clazz).equalTo("id", false).findFirst());
        } finally {
            if (realm != null)
                realm.close();
        }
    }

    public void saveListCategory(List<Category> categoryList) {
        getRealmDB().executeTransaction(realm -> realm.copyToRealmOrUpdate(categoryList));
    }

    public void saveListMovies(RealmList<VODInfo> vodInfoList, String categoryID) {
        getRealmDB().executeTransaction(realm -> {
            Category category = realm.copyFromRealm(realm.where(Category.class).equalTo("id", categoryID).findFirst());
            category.setVodInfos(vodInfoList);
            realm.copyToRealmOrUpdate(category);
            //Logger.e("vodInfoList="+vodInfoList.toString()+"SaveVODinfoList=" + category.toString());
        });
    }

    public void saveListMoviesFromCategory(RealmList<Product> productList, String categoryID) {
        getRealmDB().executeTransaction(realm -> {
            Category category = realm.copyFromRealm(realm.where(Category.class).equalTo("id", categoryID).findFirst());
            category.setProduct(productList);
            realm.copyToRealmOrUpdate(category);
            //Logger.e("productList="+productList.toString()+"SaveproductList=" + category.toString());
        });
    }

    public void saveAllChannel(List<Channel> channelList) {
        getRealmDB().executeTransaction(realm -> realm.copyToRealmOrUpdate(channelList));
    }

    public void saveListProgram(List<Program> programList) {
        getRealmDB().executeTransaction(realm -> realm.copyToRealmOrUpdate(programList));
    }

    public boolean checkCategory() {
        return getRealmDB().where(Category.class).count() > 0;
    }

    public boolean checkProduct(String categoryId) {
        Category category = getRealmDB().where(Category.class).equalTo("id", categoryId).findFirstAsync();
        return category != null && category.getProduct() != null && category.getProduct().size() > 0;
    }

    public boolean checkVodInfo(String categoryId) {
        Category category = getRealmDB().where(Category.class).equalTo("id", categoryId).findFirstAsync();
        return category != null && category.getVodInfos() != null && category.getVodInfos().size() > 0;
    }

    @Override
    public Observable<List<Category>> getCategory() {
        List<Category> categoryList = findAll(Category.class);
        //Logger.e("categoryList"+categoryList.toString());
        return Observable.just(categoryList);

        /* @Deprecated Rxjava 1
        getListObjectAsync(Category.class)
                .filter(data -> data.isLoaded() &&
                        data.isValid() &&
                        data.size() > 0)
                .map(data -> {
                    Logger.e("getCategory" + data.toString());
                    return data;
                });*/
    }

    @Override
    public Observable<List<VODInfo>> getListMovies(String categoryID) {
        return null;
    }

    public Observable<String> getListIDMovies(String categoryID) {
        Realm realmDB=getRealmDB();
        String listID ="";
        try{
            Logger.e("getMoviesProduct="+categoryID);
            RealmList<Product> products=realmDB.where(Category.class).equalTo("id",categoryID).findFirst().getProduct();
            Logger.e("products="+products.toString());
            if(products.size()>0){
                StringBuilder builder = new StringBuilder();
                for (Item item : products.get(0).getItems()) {
                    if (builder.length() == 0) builder.append(item.getItem());
                    builder.append(",").append(item.getItem());
                }
                listID=builder.toString();
            }
        }finally {
            if(realmDB!=null)
                realmDB.close();
        }
        Logger.e("listID="+listID);
        return Observable.just(listID);
       /* @Deprecated Rxjava 1
       return getObjectAsyncWithKey(Category.class, "id", categoryID)
                .map(Category::getProduct)
                .filter(data -> data.isLoaded() &&
                        data.isValid() &&
                        data.size() > 0)
                .map(products -> {
                    StringBuilder builder = new StringBuilder();
                    for (Item item : products.get(0).getItems()) {
                        if (builder.length() == 0) builder.append(item.getItem());
                        builder.append(",").append(item.getItem());
                    }
                    Logger.e("builder=" + builder.toString());
                    return builder.toString();
                });*/
    }

    @Override
    public Observable<List<VODInfo>> getMoviesFromCategory(String idList, String categoryID) {
        Realm realmDB=getRealmDB();
        List<VODInfo> vodInfoList;
        try{
            vodInfoList=realmDB.where(Category.class).equalTo("id",categoryID).findFirst().getVodInfos();
            Logger.e("vodInfoList"+vodInfoList.toString());
        }finally {
            if(realmDB!=null)
                realmDB.close();
        }
        return Observable.just(vodInfoList);
        /* @Deprecated Rxjava 1
        return getObjectAsyncWithKey(Category.class, "id", categoryID)
                .filter(data -> data.isLoaded() &&
                        data.isValid())
                .map(Category::getVodInfos);*/
    }

    @Override
    public Observable<List<Channel>> getAllChannel() {
        List<Channel> channelList = findAll(Channel.class);
        //Logger.e("getAllChannel:Realm="+channelList.toString());
        return Observable.just(channelList);
    }

    @Override
    public Observable<List<TVInfo>> getProgram(List<Channel> channelList, Date currentDate) {
        List<Program> programList = findAll(Program.class);
        //Logger.e("getProgram:Realm="+programList.toString());
        List<TVInfo> tvInfoList=new ArrayList<>();
        tvInfoList = setListNowUp(tvInfoList, channelList, programList);
        if (Preconditions.checkList(tvInfoList))
            return Observable.just(tvInfoList);
        return Observable.empty();
    }

    @Override
    public void clear() {
        getRealmDB().executeTransactionAsync(realm -> {
            realm.delete(Category.class);
            realm.delete(VODInfo.class);
            realm.delete(Channel.class);
            realm.delete(TVInfo.class);
        });
    }

    public List<TVInfo> setListNowUp(List<TVInfo> listTV, List<Channel> channelInfoList, List<Program> programInfoList) {
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
       // Collections.sort(listTV, new TVInfo());
        return listTV;
    }

    private Channel getChannelInfoOnId(List<Channel> channelInfoList, long channelId) {
        for (Channel channelInfo : channelInfoList) {
            if (channelInfo.getId() == channelId)
                return channelInfo;
        }
        return null;
    }

}
