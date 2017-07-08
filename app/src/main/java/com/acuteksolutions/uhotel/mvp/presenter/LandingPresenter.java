package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.repository.Repository;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.LandingView;
import javax.inject.Inject;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class LandingPresenter extends BasePresenter<LandingView> {
  private Repository mRepository;
  private PreferencesHelper mPreferencesHelper;
  @Inject LandingPresenter(Repository restData, PreferencesHelper preferencesHelper){
    this.mRepository=restData;
    this.mPreferencesHelper=preferencesHelper;
  }

  public void clearData(){
    this.mRepository.clear();
  }

  public PreferencesHelper getPreferencesHelper(){
    return mPreferencesHelper;
  }
}
