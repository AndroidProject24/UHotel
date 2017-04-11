package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */

@StringDef({BundleDef.TAB_MOVIES, BundleDef.LINK_LIST_MOVIES})
@Retention(RetentionPolicy.SOURCE)
public @interface BundleDef {
  String TAB_MOVIES="TAB_MOVIES";
  String LINK_LIST_MOVIES="TAB_MOVIES";
}