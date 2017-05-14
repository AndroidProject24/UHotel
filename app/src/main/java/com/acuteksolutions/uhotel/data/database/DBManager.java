package com.acuteksolutions.uhotel.data.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBManager extends SQLiteOpenHelper {

  private static String DB_PATH = "data/data/com.kctbox/databases/";
  private static String DB_NAME = "box_1_0.db";
  private SQLiteDatabase myDataBase;
  private Context myContext = null;
  private static final int Database_Version=1;
  @SuppressLint("NewApi")
  public DBManager(Context context) {
    super(context, DB_NAME, null,Database_Version);
    this.myContext = context;
  }
  public synchronized void createDataBase() throws IOException{
    try {
      this.getReadableDatabase();
      copyDataBase();
    } catch (Exception e) {
      close();
      throw new Error("Tạo Database lỗi");
    }
  }

  public void copyDataBase() throws IOException{

    //Open your local db as the input stream
    InputStream myInput = myContext.getAssets().open(DB_NAME);

    // Path to the just created empty db
    String outFileName = DB_PATH + DB_NAME;

    //Open the empty db as the output stream
    OutputStream myOutput = new FileOutputStream(outFileName);

    //transfer bytes from the inputfile to the outputfile
    byte[] buffer = new byte[1024];
    int length;
    while ((length = myInput.read(buffer))>0){
      myOutput.write(buffer, 0, length);
    }

    //Close the streams
    myOutput.flush();
    myOutput.close();
    myInput.close();
  }
  public synchronized boolean checkDataBase(){
    SQLiteDatabase checkDB = null;
    try{
      String myPath = DB_PATH + DB_NAME;
      checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }catch(SQLiteException e){
      //database does't exist yet.
    }
    if(checkDB != null){
      checkDB.close();
    }

    return checkDB != null;
  }

  public synchronized void openDataBase() throws SQLException{

    //Open the database
    String myPath = DB_PATH + DB_NAME;
    myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

  }

  @Override
  public synchronized void close() {
    if(myDataBase != null)
      myDataBase.close();
    super.close();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
  }
  //Upgrade database vesion new
  @Override
  public void onUpgrade(SQLiteDatabase arg0, int Database_Vesion, int newVersion) {
    int upgradeTo = Database_Vesion + 1;
    while (upgradeTo <= newVersion)
    {
      myContext.deleteDatabase(DB_NAME);
      this.getReadableDatabase();
      try {
        copyDataBase();
      } catch (IOException e) {
        throw new Error("Tạo Database lỗi");
      }
      upgradeTo++;
    }
    Log.wtf("vesion"+Database_Vesion+"up"+newVersion,"hehe");
  }

  /*static DBSong getSongFromCursor(Cursor cursor) {
    DBSong song = new DBSong();
    song.setId(cursor.getInt(cursor.getColumnIndex("song_id")));
    song.setName(cursor.getString(cursor.getColumnIndex("name")));
    song.setName_clean(cursor.getString(cursor.getColumnIndex("name_clean")));
    song.setShortname(cursor.getString(cursor.getColumnIndex("shortname")));
    song.setLyric(cursor.getString(cursor.getColumnIndex("lyric")));
    song.setLyric_text(cursor.getString(cursor.getColumnIndex("lyric_text")));
    song.setLyric_text_clean(cursor.getString(cursor.getColumnIndex("lyric_text_clean")));
    song.setImage(cursor.getString(cursor.getColumnIndex("image")));
    song.setFormat(cursor.getInt(cursor.getColumnIndex("format")));
    song.setComposer(cursor.getString(cursor.getColumnIndex("composer")));
    song.setSinger(cursor.getString(cursor.getColumnIndex("singer")));
    song.setSinger_id(cursor.getString(cursor.getColumnIndex("singer_id")));
    song.setThumbnail(cursor.getString(cursor.getColumnIndex("thumbnail")));
    song.setBeat(cursor.getString(cursor.getColumnIndex("beat")));
    song.setMv(cursor.getString(cursor.getColumnIndex("mv")));
    song.setMp3(cursor.getString(cursor.getColumnIndex("mp3")));
    song.setDual(cursor.getInt(cursor.getColumnIndex("dual")));
    song.setCategory_list(cursor.getString(cursor.getColumnIndex("category_list")));
    song.setOccasion_list(cursor.getString(cursor.getColumnIndex("occasion_list")));
    song.setUpdated_time(cursor.getString(cursor.getColumnIndex("updated_time")));
    song.setUpdate(UpdateDef.UPDATE_OK);
    return song;
  }

  static DBPlaylist getPlaylistFromCursor(Cursor cursor) {
    DBPlaylist playlist = new DBPlaylist();
    playlist.setId(cursor.getInt(cursor.getColumnIndex("playlist_id")));
    playlist.setName(cursor.getString(cursor.getColumnIndex("name")));
    playlist.setImage(cursor.getString(cursor.getColumnIndex("image")));
    playlist.setName_clean(cursor.getString(cursor.getColumnIndex("name_clean")));
    playlist.setSinger(cursor.getString(cursor.getColumnIndex("singer")));
    playlist.setList_songs(cursor.getString(cursor.getColumnIndex("list_songs")));
    return playlist;
  }
  static DBSinger getSingerFromCursor(Cursor cursor) {
    DBSinger singer = new DBSinger();
    singer.setId(cursor.getInt(cursor.getColumnIndex("singer_id")));
    singer.setName(cursor.getString(cursor.getColumnIndex("name")));
    singer.setImage(cursor.getString(cursor.getColumnIndex("image")));
    singer.setName_clean(cursor.getString(cursor.getColumnIndex("name_clean")));
    singer.setShortname(cursor.getString(cursor.getColumnIndex("shortname")));
    singer.setCover_image(cursor.getString(cursor.getColumnIndex("cover_image")));
    return singer;
  }*/
}
