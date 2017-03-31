package com.acuteksolutions.uhotel.intdef;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toan.IT
 * Created by vantoan on 3/26/17.
 * Email: Huynhvantoan.itc@gmail.com
 */
@StringDef({PathDef.REGION_UID, PathDef.CAT_ID, PathDef.LIST_ID})
@Retention(RetentionPolicy.CLASS)
public @interface PathDef {
  String REGION_UID="region_uid";
  String CAT_ID="catId";
  String LIST_ID="id_list";
}
