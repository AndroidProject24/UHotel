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

public class TabSettingDef {
  @Retention(RetentionPolicy.RUNTIME)

  @IntDef({ ACCOUNT,DEVICES})
  public @interface TabFood{
    int ACCOUNT=0;
    int DEVICES=1;
  }

  public static final int ACCOUNT = R.string.tab_account;
  public static final int DEVICES=R.string.tab_devices;

  private int tab=ACCOUNT;

  @TabFood
  public int getTab(int index){
    switch (index){
      case TabFood.ACCOUNT:
        tab=ACCOUNT;
        break;
      case TabFood.DEVICES:
        tab=DEVICES;
        break;
    }
    return tab;
  }

  @SuppressWarnings("unused")
  public void setTab(@TabFood int tab){
    this.tab=tab;
  }

  public int tabSize(){
    return 2;
  }
}
