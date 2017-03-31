package com.acuteksolutions.uhotel.intdef;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toan.IT
 * Created by vantoan on 3/26/17.
 * Email: Huynhvantoan.itc@gmail.com
 */
@StringDef({LinkDef.LINK_LIST_CATEGORY, LinkDef.LINK_LIST_MOVIES})
@Retention(RetentionPolicy.CLASS)
public @interface LinkDef {
  String LINK_LIST_CATEGORY="/restapi/rest/region_uid/store/categories";
  String LINK_LIST_MOVIES="/vod/info?purchase_item_list=id_list";
  String LINK_MOVIES_DETAILS="/restapi/rest/region_uid/store/products?purchase_category_id=catId";
}