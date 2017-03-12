package com.acuteksolutions.uhotel.intdef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by vantoan on 2/25/17.
 * Email: huynhvantoan.itc@gmail.com
 */
@android.support.annotation.StringDef({KhoSimIndexDef.SIM_TRA_TRUOC, KhoSimIndexDef.SIM_TRA_SAU, KhoSimIndexDef.SIM_TRA_TRUOC_DEP, KhoSimIndexDef.SIM_DEP})
@Retention(RetentionPolicy.CLASS)
public @interface KhoSimIndexDef {
  String SIM_TRA_TRUOC= "tratruoc";
  String SIM_TRA_SAU= "trasau";
  String SIM_TRA_TRUOC_DEP= "tratruocsodep";
  String SIM_DEP="camket";
}