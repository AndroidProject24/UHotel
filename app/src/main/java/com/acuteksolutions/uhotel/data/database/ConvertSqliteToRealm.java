package com.acuteksolutions.uhotel.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import io.realm.Realm;
import java.io.IOException;

/**
 * Toan.IT
 * Created by vantoan on 3/14/17.
 * Email: Huynhvantoan.itc@gmail.com
 */

public class ConvertSqliteToRealm {
  //SAVE DATABASE
  /*RealmBackupRestore realmBackupRestore=new RealmBackupRestore(getActivity(),databaseRealm);
  realmBackupRestore.backup();*/
  public static void convert(Context context,Realm realm){
    realm.executeTransactionAsync(bgRealm -> {
      /////Import Emotion
      /*try{
        InputStream stream = context.getAssets().open("emotion.json");
        bgRealm.createAllFromJson(EmotionDB.class, stream);
        if (stream != null) {
          stream.close();
        }
      }catch (Exception e){
        e.printStackTrace();
      }*/
      /////Import Data
      DBManager connection = new DBManager(context);
      try {
        connection.createDataBase();
      } catch (IOException e1) {
        e1.printStackTrace();
        connection.close();
      }
      SQLiteDatabase db = connection.getWritableDatabase();
      Cursor cursorSong = db.rawQuery("SELECT * FROM song", null);
      Cursor cursorSinger = db.rawQuery("SELECT * FROM singer", null);
      Cursor cursorPlaylist= db.rawQuery("SELECT * FROM playlist", null);
      Cursor cursorHome=db.rawQuery("SELECT * FROM box_api", null);
      if (connection.checkDataBase()) {
        connection.openDataBase();
      }
     /* if (cursorSong.moveToFirst()) {
        do {
          DBSong dbSong = DBManager.getSongFromCursor(cursorSong);
          bgRealm.copyToRealmOrUpdate(dbSong);
        }
        while (cursorSong.moveToNext());{}
      }
      if (cursorSinger.moveToFirst()) {
        do {
          DBSinger dbSinger = DBManager.getSingerFromCursor(cursorSinger);
          bgRealm.copyToRealmOrUpdate(dbSinger);
        }
        while (cursorSinger.moveToNext());{}
      }
      if (cursorPlaylist.moveToFirst()) {
        do {
          DBPlaylist dbPlaylist = DBManager.getPlaylistFromCursor(cursorPlaylist);
          bgRealm.copyToRealmOrUpdate(dbPlaylist);
        }
        while (cursorPlaylist.moveToNext());{}
      }
      if(cursorHome.getColumnIndex("data")!=0 && cursorHome.moveToFirst()){
        bgRealm.createObjectFromJson(HomeUpdate.class,cursorHome.getString(cursorHome.getColumnIndex("data")));
        bgRealm.where(HomeUpdate.class).findAll().first().setVersionDB(InstallApp.getAppVersionCode(context));
        Logger.e("version="+InstallApp.getAppVersionCode(context)+"getVersionDB="+bgRealm.where(HomeUpdate.class).findAll().first().getVersionDB());
      }*/
      cursorSong.close();
      cursorSinger.close();
      cursorPlaylist.close();
      cursorHome.close();
      db.close();
      connection.close();
    }, () -> Logger.d("InsertDatabase"+ "onSuccess!!!"), Throwable::printStackTrace);
  }
}
