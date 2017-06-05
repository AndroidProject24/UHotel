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

public class TabFoodDef {
  @Retention(RetentionPolicy.RUNTIME)

  @IntDef({ COFFEE,BEST,TOP, HOTEL})
  public @interface TabFood{
    int COFFEE=0;
    int BEST=1;
    int TOP=2;
    int HOTEL=3;
  }

  public static final int COFFEE = R.string.food_menu_coffee;
  public static final int BEST=R.string.food_menu_best;
  public static final int TOP = R.string.food_menu_top;
  public static final int HOTEL = R.string.food_menu_hotel;

  private int tab=COFFEE;

  @TabFood
  public int getTab(int index){
    switch (index){
      case TabFood.COFFEE:
        tab=COFFEE;
        break;
      case TabFood.BEST:
        tab=BEST;
        break;
      case TabFood.TOP:
        tab=TOP;
        break;
      case TabFood.HOTEL:
        tab=HOTEL;
        break;
    }
    return tab;
  }

  @SuppressWarnings("unused")
  public void setTab(@TabFood int tab){
    this.tab=tab;
  }

  public int tabSize(){
    return 4;
  }
}
