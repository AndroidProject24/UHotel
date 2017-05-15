package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.repository.Repository;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.RoomConciergeView;
import javax.inject.Inject;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class RoomPresenter extends BasePresenter<RoomConciergeView> {
  private Repository mRepository;
  private PreferencesHelper mPreferencesHelper;
  @Inject RoomPresenter(Repository restData, PreferencesHelper preferencesHelper){
    this.mRepository=restData;
    this.mPreferencesHelper=preferencesHelper;
  }

  public PreferencesHelper getPreferencesHelper(){
    return mPreferencesHelper;
  }
}
