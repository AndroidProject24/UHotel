package com.acuteksolutions.uhotel.intdef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */

@IntDef({BitmapDef.CMND1, BitmapDef.CMND2, BitmapDef.HD1,
        BitmapDef.HD2,BitmapDef.PL4,BitmapDef.GPKD})
@Retention(RetentionPolicy.CLASS)
public @interface BitmapDef {
  int CMND1=1;
  int CMND2=2;
  int HD1=3;
  int HD2=4;
  int PL4=5;
  int GPKD=6;
}
