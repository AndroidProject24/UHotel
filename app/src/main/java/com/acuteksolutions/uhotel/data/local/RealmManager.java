package com.acuteksolutions.uhotel.data.local;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObject;
import io.realm.RealmSchema;
import java.util.List;
import javax.inject.Inject;


public class RealmManager {
  public RealmConfiguration mRealmConfigDB;
  @Inject
  public RealmManager() {
    mRealmConfigDB = new RealmConfiguration.Builder()
            .schemaVersion(1)
            .migration(migrationDB)
            .deleteRealmIfMigrationNeeded()
            .build();
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

  public <T extends RealmObject> T findFist(Class<T> clazz) {
    Realm realm=null;
    try{
      realm=getRealmDB();
      return realm.copyFromRealm(realm.where(clazz).equalTo("id", false).findFirst());
    }finally {
      if(realm!=null)
        realm.close();
    }
  }

}
