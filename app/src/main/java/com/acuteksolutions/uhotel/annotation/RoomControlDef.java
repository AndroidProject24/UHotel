package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toan.IT
 * Created by vantoan on 3/26/17.
 * Email: Huynhvantoan.itc@gmail.com
 */
@StringDef({ RoomControlDef.MAIN, RoomControlDef.OVERHEAD, RoomControlDef.WALL, RoomControlDef.SHEERS,
            RoomControlDef.BLACKOUT,RoomControlDef.SLIDER,RoomControlDef.TEMP})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoomControlDef {
  String MAIN="mainseek";
  String OVERHEAD="overheadseek";
  String WALL="wallseek";
  String SHEERS="sheersseek";
  String BLACKOUT="blackoutseek";
  String SLIDER="sliderseek";
  String TEMP="tempseek";
}
