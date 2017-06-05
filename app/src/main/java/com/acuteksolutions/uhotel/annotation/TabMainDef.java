package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.IntDef;
import com.acuteksolutions.uhotel.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toan.IT
 * Created by vantoan on 3/26/17.
 * Email: Huynhvantoan.itc@gmail.com
 */

public class TabMainDef {
  @Retention(RetentionPolicy.RUNTIME)

  @IntDef({ HOME,CONCIERGE,LIVETV, MOVIES, FOOD, ROOMCONTROL})
  public @interface TabMain{
    int HOME=0;
    int CONCIERGE=1;
    int LIVETV=2;
    int MOVIES=3;
    int FOOD=4;
    int ROOMCONTROL=5;
  }

  public static final int HOME = R.string.home_menu_home;
  public static final int CONCIERGE=R.string.home_menu_concierge;
  public static final int LIVETV = R.string.home_menu_live_tv;
  public static final int MOVIES = R.string.home_menu_movies;
  public static final int FOOD = R.string.home_menu_food_activity;
  public static final int ROOMCONTROL = R.string.home_menu_room_control;

  private int tab=HOME;

  @TabMain
  public int getTab(int index){
    switch (index){
      case TabMain.HOME:
        tab=HOME;
        break;
      case TabMain.CONCIERGE:
        tab=CONCIERGE;
        break;
      case TabMain.LIVETV:
        tab=LIVETV;
        break;
      case TabMain.MOVIES:
        tab=MOVIES;
        break;
      case TabMain.FOOD:
        tab=FOOD;
        break;
      case TabMain.ROOMCONTROL:
        tab=ROOMCONTROL;
        break;
    }
    return tab;
  }

  @SuppressWarnings("unused")
  public void setTab(@TabMain int tab){
    this.tab=tab;
  }

  public int tabSize(){
    return 6;
  }

}
