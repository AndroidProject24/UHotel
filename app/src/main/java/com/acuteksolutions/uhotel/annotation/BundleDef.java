package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */

@StringDef({BundleDef.BUNDLE, BundleDef.BUNDLE_KEY, BundleDef.BUNDLE_MOVIES_TITLE, BundleDef.BUNDLE_MOVIES_CHANNEL, BundleDef.IS_CORRECT})
@Retention(RetentionPolicy.RUNTIME)
public @interface BundleDef {
  String BUNDLE="BUNDLE";
  String BUNDLE_KEY="BUNDLE_KEY";
  String BUNDLE_MOVIES_TITLE="BUNDLE_MOVIES_TITLE";
  String BUNDLE_MOVIES_CHANNEL="BUNDLE_MOVIES_CHANNEL";
  String IS_CORRECT="isCorrect";
}