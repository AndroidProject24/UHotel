package com.acuteksolutions.uhotel.data.local;

import android.text.TextUtils;

import com.acuteksolutions.uhotel.data.repository.DataSource;
import com.acuteksolutions.uhotel.mvp.model.movies.Category;
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
        Realm realmDB=null;
        try{
            realmDB=getRealmDB();
            realmDB.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(categoryList));
        }finally {
            if(realmDB!=null)
                realmDB.close();
        }
    }

    public void saveListMovies(List<VODInfo> vodInfoList) {
        Realm realmDB=null;
        try{
            realmDB=getRealmDB();
            realmDB.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(vodInfoList));
        }finally {
            if(realmDB!=null)
                realmDB.close();
        }
    }

    public void saveListMoviesDetails(RealmList<Product> productList, String categoryID) {
        Realm realmDB=null;
        try{
            realmDB=getRealmDB();
            realmDB.executeTransactionAsync(realm -> {
                Category category=realm.where(Category.class).equalTo("id", categoryID).findFirst();
                category.setProduct(productList);
                realm.copyToRealmOrUpdate(category);
            });
        }finally {
            if(realmDB!=null)
                realmDB.close();
        }
    }

    @Override
    public Observable<List<Category>> getCategory() {
        List<Category> categoryList = findAll(Category.class);
        return Observable.just(categoryList);
    }

    @Override
    public Observable<List<VODInfo>> getListMovies(String idList,String categoryID) {
        List<VODInfo> vodInfoList = findAllWithKey(VODInfo.class,categoryID);
        return Observable.just(vodInfoList);
    }

    public Observable<String> getMoviesProduct(String categoryID) {
        Realm realmDB=null;
        String listID ="";
        try{
            realmDB=getRealmDB();
            RealmList<Product> products=realmDB.where(Category.class).equalTo("id",categoryID).findFirst().getProduct();
            if(products.size()>0){
                listID=TextUtils.join(",",  products.get(0).getItems());
            }
        }finally {
            if(realmDB!=null)
                realmDB.close();
        }
        return Observable.just(listID);
    }

    @Override
    public Observable<List<VODInfo>> getMoviesDetails(String catID) {
       return null;
    }
}
