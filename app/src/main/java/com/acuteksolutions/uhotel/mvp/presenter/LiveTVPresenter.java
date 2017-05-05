package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.service.RestData;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.LiveTvView;
import javax.inject.Inject;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class LiveTVPresenter extends BasePresenter<LiveTvView> {
  private RestData mRestData;
  private PreferencesHelper mPreferencesHelper;
  @Inject LiveTVPresenter(RestData restData, PreferencesHelper preferencesHelper){
    this.mRestData=restData;
    this.mPreferencesHelper=preferencesHelper;
  }

  public PreferencesHelper getPreferencesHelper(){
    return mPreferencesHelper;
  }
}
