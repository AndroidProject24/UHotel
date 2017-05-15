package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */

@StringDef({BundleDef.BUNDLE_KEY, BundleDef.IS_CORRECT})
@Retention(RetentionPolicy.SOURCE)
public @interface BundleDef {
  String BUNDLE_KEY="BUNDLE_KEY";
  String IS_CORRECT="isCorrect";
}