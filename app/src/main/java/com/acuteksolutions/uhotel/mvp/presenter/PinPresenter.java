package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.data.service.RestData;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.PinView;
import javax.inject.Inject;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class PinPresenter extends BasePresenter<PinView> {
  private RestData mRestData;
  private PreferencesHelper mPreferencesHelper;
  @Inject PinPresenter(RestData restData, PreferencesHelper preferencesHelper){
    this.mRestData=restData;
    this.mPreferencesHelper=preferencesHelper;
  }

  public void verifyPin(String pin) {
    addSubscribe(mRestData.verifyPin(pin)
        .doOnSubscribe(() -> getMvpView().showLoading())
        .doOnCompleted(() -> getMvpView().hideLoading())
        .subscribe(new DefaultObserver<Boolean>() {
          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            getMvpView().showError(e.getMessage());
          }

          @Override
          public void onNext(Boolean aBoolean) {
            getMvpView().verifyPin(aBoolean);
          }
        }));
  }

  public void changePin(String pinNew,String pinOld) {
    addSubscribe(mRestData.changePin(pinNew,pinOld)
        .doOnSubscribe(() -> getMvpView().showLoading())
        .doOnCompleted(() -> getMvpView().hideLoading())
        .subscribe(new DefaultObserver<Boolean>() {
          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            getMvpView().showError(e.getMessage());
          }

          @Override
          public void onNext(Boolean aBoolean) {
            getMvpView().changePin(aBoolean);
          }
        }));
  }

  public void saveSetting(String data,String pin) {
    addSubscribe(mRestData.saveSetting(data,pin)
        .doOnSubscribe(() -> getMvpView().showLoading())
        .doOnCompleted(() -> getMvpView().hideLoading())
        .subscribe(new DefaultObserver<Boolean>() {
          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            getMvpView().showError(e.getMessage());
          }

          @Override
          public void onNext(Boolean aBoolean) {
            getMvpView().saveSetting(aBoolean);
          }
        }));
  }
  public PreferencesHelper getPreferencesHelper(){
    return mPreferencesHelper;
  }
}
