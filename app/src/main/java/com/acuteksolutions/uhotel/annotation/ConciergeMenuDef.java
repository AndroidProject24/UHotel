package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toan.IT
 * Created by vantoan on 3/26/17.
 * Email: Huynhvantoan.itc@gmail.com
 */
@IntDef({
    ConciergeMenuDef.ROOM, ConciergeMenuDef.CAR_RENTAL, ConciergeMenuDef.CHECK_OUT, ConciergeMenuDef.BUSINESS,
    ConciergeMenuDef.HOUSE, ConciergeMenuDef.VALET, ConciergeMenuDef.MIRROR, ConciergeMenuDef.PARENTAL})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConciergeMenuDef {
  int ROOM=0;
  int CAR_RENTAL=1;
  int CHECK_OUT=2;
  int BUSINESS = 3;
  int HOUSE=4;
  int VALET=5;
  int MIRROR=6;
  int PARENTAL = 7;
}