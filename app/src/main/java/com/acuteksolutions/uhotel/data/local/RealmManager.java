package com.acuteksolutions.uhotel.data.local;

import com.acuteksolutions.uhotel.data.repository.DataSource;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.Item;
import com.acuteksolutions.uhotel.mvp.model.movies.Product;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmSchema;
import rx.Observable;


public class RealmManager implements DataSource{
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
        Realm realm=null;
        try{
            realm=getRealmDB();
            return realm.copyFromRealm(realm.where(clazz).findAll());
        }finally {
            if(realm!=null)
                realm.close();
        }
    }

    public <T extends RealmObject> List<T> findAllWithKey(Class<T> clazz,String categoryID) {
        Realm realm=null;
        try{
            realm=getRealmDB();
            return realm.copyFromRealm(realm.where(clazz).equalTo("id",categoryID).findAll());
        }finally {
            if(realm!=null)
                realm.close();
        }
    }

    public <T extends RealmObject> T findFist(Class<T> clazz) {
        Realm realm=null;
        try{
            realm=getRealmDB();
            return realm.copyFromRealm(realm.where(clazz).findFirst());
        }finally {
            if(realm!=null)
                realm.close();
        }
    }

    public <T extends RealmObject> T findFistWithKey(Class<T> clazz) {
        Realm realm=null;
        try{
            realm=getRealmDB();
            return realm.copyFromRealm(realm.where(clazz).equalTo("id", false).findFirst());
        }finally {
            if(realm!=null)
                realm.close();
        }
    }

    public void saveListCategory(List<Category> categoryList) {
        getRealmDB().executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(categoryList));
    }

    public void saveListMovies(RealmList<VODInfo> vodInfoList,String categoryID) {
        getRealmDB().executeTransactionAsync(realm -> {
            Category category=realm.where(Category.class).equalTo("id", categoryID).findFirst();
            RealmList<VODInfo> list = vodInfoList;
            if (!list.isManaged()) { // if the 'list' is managed, all items in it is also managed
                RealmList<VODInfo> managedImageList = new RealmList<>();
                for (VODInfo item : list) {
                    if (item.isManaged()) {
                        managedImageList.add(item);
                    } else {
                        managedImageList.add(realm.copyToRealm(item));
                    }
                }
                list = managedImageList;
            }
            category.setVodInfos(list);
            Logger.e("SaveVODinfoList="+category.toString());
            realm.copyToRealmOrUpdate(category);
        });
    }

    public void saveListMoviesDetails(final RealmList<Product> productList, String categoryID) {
        getRealmDB().executeTransactionAsync(realm -> {
            Category category=realm.where(Category.class).equalTo("id", categoryID).findFirst();
            RealmList<Product> list = productList;
            if (!list.isManaged()) { // if the 'list' is managed, all items in it is also managed
                RealmList<Product> managedImageList = new RealmList<>();
                for (Product item : list) {
                    if (item.isManaged()) {
                        managedImageList.add(item);
                    } else {
                        managedImageList.add(realm.copyToRealm(item));
                    }
                }
                list = managedImageList;
            }
            category.setProduct(list);
            Logger.e("SaveproductList="+category.toString());
            realm.copyToRealmOrUpdate(category);
        });
    }

    @Override
    public Observable<List<Category>> getCategory() {
        List<Category> categoryList = findAll(Category.class);
        return Observable.just(categoryList);
    }

    @Override
    public Observable<List<VODInfo>> getListMovies(String idList,String categoryID) {
        Realm realmDB=null;
        List<VODInfo> vodInfoList;
        try{
            realmDB=getRealmDB();
            vodInfoList=realmDB.where(Category.class).equalTo("id",categoryID).findFirst().getVodInfos();
        }finally {
            if(realmDB!=null)
                realmDB.close();
        }
        return Observable.just(vodInfoList);
    }

    public Observable<String> getMoviesProduct(String categoryID) {
        Realm realmDB=null;
        String listID ="";
        try{
            realmDB=getRealmDB();
            Logger.e("getMoviesProduct="+categoryID);
            RealmList<Product> products=realmDB.where(Category.class).equalTo("id",categoryID).findFirst().getProduct();
            Logger.e("products="+products.toString());
            if(products.size()>0){
                //listID=TextUtils.join(",",  products.get(0).getItems());
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
    }

    @Override
    public Observable<List<VODInfo>> getMoviesDetails(String catID) {
       return null;
    }
}
