package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Toan.IT on 5/14/17.
 * Email: huynhvantoan.itc@gmail.com
 */

@StringDef({ParentalItemDef.WATCH_TV,ParentalItemDef.MOVIES, ParentalItemDef.CONCIERGE,
            ParentalItemDef.FNA, ParentalItemDef.ROOM_CONTROL})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentalItemDef {
  String WATCH_TV="watchTvState";
  String MOVIES="moviesState";
  String CONCIERGE="conciergeState";
  String FNA="fnaState";
  String ROOM_CONTROL="roomControlState";
}
