package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.repository.Repository;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.LoginView;
import javax.inject.Inject;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class LoginPresenter extends BasePresenter<LoginView> {
  private Repository mRepository;
  private PreferencesHelper mPreferencesHelper;
  @Inject
  LoginPresenter(Repository restData, PreferencesHelper preferencesHelper){
    this.mRepository=restData;
    this.mPreferencesHelper=preferencesHelper;
  }

  public void login(String pass){
      getMvpView().showLoading();
      addSubscribe(mRepository.getLogin(pass)
              .subscribeWith(new DefaultObserver<Login>() {
                  @Override
                  public void onError(Throwable e) {
                      e.printStackTrace();
                      getMvpView().hideLoading();
                      getMvpView().showError("1");
                  }

                  @Override
                  public void onNext(Login login) {
                      try {
                          getMvpView().hideLoading();
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

    public PreferencesHelper getmPreferencesHelper() {
        return mPreferencesHelper;
    }
}
