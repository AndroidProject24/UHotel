package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toan.IT
 * Created by vantoan on 3/26/17.
 * Email: Huynhvantoan.itc@gmail.com
 */
@StringDef({PathDef.REGION_UID, PathDef.PROFILE_ID, PathDef.CAT_ID, PathDef.LIST_ID,
    PathDef.CHANNEL_ID, PathDef.DATE, PathDef.PAGE_SIZE, PathDef.CID})
@Retention(RetentionPolicy.RUNTIME)
public @interface PathDef {
  String REGION_UID="region_uid";
  String PROFILE_ID="profileId";
  String CAT_ID="catId";
  String LIST_ID="id_list";
  String CHANNEL_ID="Channel_id";
  String DATE="Date";
  String PAGE_SIZE="Page_size";
  String CID="cid";
}
