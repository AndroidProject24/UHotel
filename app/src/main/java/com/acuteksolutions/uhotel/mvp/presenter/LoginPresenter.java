package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.data.service.RestData;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.LoginView;

import javax.inject.Inject;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class LoginPresenter extends BasePresenter<LoginView> {
  private RestData mRestData;
  private PreferencesHelper mPreferencesHelper;
  @Inject
  LoginPresenter(RestData restData, PreferencesHelper preferencesHelper){
    this.mRestData=restData;
    this.mPreferencesHelper=preferencesHelper;
  }

  public void login(String pass){
    addSubscribe(mRestData.getLogin(pass)
            .doOnSubscribe(() -> getMvpView().showLoading())
            .doOnCompleted(() -> getMvpView().hideLoading())
            .subscribe(new DefaultObserver<Login>() {
              @Override
              public void onError(Throwable e) {
                e.printStackTrace();
                getMvpView().showError("1");
              }

              @Override
              public void onNext(Login login) {
                try {
                  if(login!=null) {
                   getMvpView().loginSucess();
                    mPreferencesHelper.putJsonLogin(login);
                  }else
                    getMvpView().loginError();
                }catch (Exception e){
                  e.printStackTrace();
                }
              }
            }));
  }
}