package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toan.IT
 * Created by vantoan on 3/26/17.
 * Email: Huynhvantoan.itc@gmail.com
 */
@StringDef({ParseGsonDef.ID,ParseGsonDef.ARRAY, ParseGsonDef.OBJECT,ParseGsonDef.ITEMS, ParseGsonDef.MEDIA_RESOURCES})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParseGsonDef {
  String ID="id";
  String ARRAY="list";
  String OBJECT="object";
  String ITEMS="purchaseItems";
  String MEDIA_RESOURCES="mediaResources";
}
