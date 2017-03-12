package com.acuteksolutions.uhotel.intdef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */
@android.support.annotation.IntDef({TheloaiDef.THU_TUC, TheloaiDef.HOA_MANG_TRA_TRUOC, TheloaiDef.HOA_MANG_TRA_SAU, TheloaiDef.CHUONG_TRINH_KHUYEN_MAI,
        TheloaiDef.CTKM_TRA_SAU,TheloaiDef.KMTS_DOANH_NGHIEP,TheloaiDef.KMTS_CA_NHAN,TheloaiDef.CTKM_CAM_KET_SO_DEP,TheloaiDef.KHUYEN_MAI_NAP_THE_NGAY})
@Retention(RetentionPolicy.SOURCE)
public @interface TheloaiDef {
  int THU_TUC= 113;
  int HOA_MANG_TRA_TRUOC=120;
  int HOA_MANG_TRA_SAU=119;
  int CHUONG_TRINH_KHUYEN_MAI=112;
  int CTKM_TRA_SAU= 116;
  int KMTS_DOANH_NGHIEP=123;
  int KMTS_CA_NHAN=124;
  int CTKM_CAM_KET_SO_DEP=117;
  int KHUYEN_MAI_NAP_THE_NGAY=118;
}