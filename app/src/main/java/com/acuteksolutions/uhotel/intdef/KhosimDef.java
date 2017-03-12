package com.acuteksolutions.uhotel.intdef;


import com.acuteksolutions.uhotel.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */
@android.support.annotation.IntDef({KhosimDef.SIM_TRA_TRUOC, KhosimDef.SIM_TRA_SAU,KhosimDef.SIM_TRA_TRUOC_DEP,KhosimDef.SIM_DEP})
@Retention(RetentionPolicy.SOURCE)
public @interface KhosimDef {
  int SIM_TRA_TRUOC= R.string.kho_sim_tratruoc;
  int SIM_TRA_SAU=R.string.kho_sim_trasau;
  int SIM_TRA_TRUOC_DEP=R.string.kho_sim_tratruoc_dep;
  int SIM_DEP=R.string.kho_sim_dep;
}