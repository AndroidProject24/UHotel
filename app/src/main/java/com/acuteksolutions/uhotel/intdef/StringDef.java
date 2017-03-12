package com.acuteksolutions.uhotel.intdef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */
@android.support.annotation.StringDef({StringDef.BUNDLE_TITLE, StringDef.BUNDLE_DATA})
@Retention(RetentionPolicy.CLASS)
public @interface StringDef {
  String BUNDLE_TITLE="BUNDLE_TITLE";
  String BUNDLE_DATA="BUNDLE_DATA";
}
