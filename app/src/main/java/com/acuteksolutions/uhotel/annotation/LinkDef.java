package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toan.IT
 * Created by vantoan on 3/26/17.
 * Email: Huynhvantoan.itc@gmail.com
 */
@StringDef({LinkDef.LINK_LIST_CATEGORY, LinkDef.LINK_LIST_MOVIES,LinkDef.LINK_MOVIES_DETAILS,
    LinkDef.LINK_IMAGE_URL,LinkDef.LINK_LIVE_ALL_CHANNEL,LinkDef.LINK_LIVE_PROGRAM_BY_ID_PATH, LinkDef.LINK_STREAM})
@Retention(RetentionPolicy.RUNTIME)
public @interface LinkDef {
  String LINK_LIST_CATEGORY="/restapi/rest/region_uid/store/categories";
  String LINK_LIST_MOVIES="/vod/info?purchase_item_list=id_list";
  String LINK_MOVIES_DETAILS="/restapi/rest/region_uid/store/products?purchase_category_id=catId";
  String LINK_IMAGE_URL = "http://bsdev.acuteksolutions.com/restapi/rest/regionId/images/";
  String LINK_LIVE_ALL_CHANNEL="/restapi/rest/region_uid/channels";
  String LINK_LIVE_PROGRAM_BY_ID_PATH="/restapi/rest/region_uid/tvprogram?date=Date";
  String LINK_STREAM="/restapi/rest/region_uid/content/media?include_media_resources=true&content_info_id=cid";
}