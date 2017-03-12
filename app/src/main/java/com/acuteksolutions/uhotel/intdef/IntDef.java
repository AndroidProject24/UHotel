package com.acuteksolutions.uhotel.intdef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */
@android.support.annotation.IntDef({IntDef.ONE, IntDef.TWO, IntDef.THREE,
        IntDef.FOUR, IntDef.FIVE})
@Retention(RetentionPolicy.CLASS)
public @interface IntDef {
  int ONE=1;
  int TWO=2;
  int THREE=3;
  int FOUR=4;
  int FIVE=5;
}
