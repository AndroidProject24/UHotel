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

public class TabMoviesDef {
  @Retention(RetentionPolicy.RUNTIME)

  @IntDef({ LATEST,ACTION,ADULTS, COMEDY, DRAMA, EVENTS, FAMILY})
  public @interface TabMovies{
    int LATEST=0;
    int ACTION=1;
    int ADULTS=2;
    int COMEDY=3;
    int DRAMA=4;
    int EVENTS=5;
    int FAMILY=6;
  }

  public static final int LATEST = R.string.movies_menu_latest;
  public static final int ACTION=R.string.movies_menu_action;
  public static final int ADULTS = R.string.movies_menu_adults;
  public static final int COMEDY = R.string.movies_menu_comedy;
  public static final int DRAMA = R.string.movies_menu_drama;
  public static final int EVENTS = R.string.movies_menu_events;
  public static final int FAMILY = R.string.movies_menu_family;

  private int tab=LATEST;

  @TabMovies
  public int getTab(int index){
    switch (index){
      case TabMovies.LATEST:
        tab=LATEST;
        break;
      case TabMovies.ACTION:
        tab=ACTION;
        break;
      case TabMovies.ADULTS:
        tab=ADULTS;
        break;
      case TabMovies.COMEDY:
        tab=COMEDY;
        break;
      case TabMovies.DRAMA:
        tab=DRAMA;
        break;
      case TabMovies.EVENTS:
        tab=EVENTS;
        break;
      case TabMovies.FAMILY:
        tab=FAMILY;
        break;
    }
    return tab;
  }

  @SuppressWarnings("unused")
  public void setTab(@TabMovies int tab){
    this.tab=tab;
  }

  public int tabSize(){
    return 7;
  }
}
