package com.acuteksolutions.uhotel.annotation;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toan.IT
 * Created by vantoan on 3/26/17.
 * Email: Huynhvantoan.itc@gmail.com
 */
@IntDef({
    ParentalPinDef.ONLY_VERIFY, ParentalPinDef.VERIFY_CHANGE_PIN, ParentalPinDef.CHANGE_PIN})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentalPinDef {
  int ONLY_VERIFY=1;
  int CHANGE_PIN=2;
  int VERIFY_CHANGE_PIN=3;
}