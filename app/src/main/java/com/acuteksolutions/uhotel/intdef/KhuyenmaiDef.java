package com.acuteksolutions.uhotel.intdef;


import com.acuteksolutions.uhotel.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */
@android.support.annotation.IntDef({KhuyenmaiDef.KHUYEN_MAI_TRA_SAU, KhuyenmaiDef.KHUYEN_MAI_SO_DEP, KhuyenmaiDef.KHUYEN_MAI_TRONG_NGAY})
@Retention(RetentionPolicy.SOURCE)
public @interface KhuyenmaiDef {
  int KHUYEN_MAI_TRA_SAU= R.string.km_trasau;
  int KHUYEN_MAI_SO_DEP=R.string.km_sodep;
  int KHUYEN_MAI_TRONG_NGAY=R.string.km_trongngay;
}