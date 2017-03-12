package com.acuteksolutions.uhotel.mvp.presenter.movies;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.data.service.RestData;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.movies.MoviesView;

import javax.inject.Inject;

import okhttp3.MultipartBody;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class MoviesPresenter extends BasePresenter<MoviesView> {
  private RestData mRestData;
  private PreferencesHelper mPreferencesHelper;
  @Inject
  MoviesPresenter(RestData restData, PreferencesHelper preferencesHelper){
    this.mRestData=restData;
    this.mPreferencesHelper=preferencesHelper;
  }

  public void uploadTratruocTest(String sdt, String idDV, String cmnd_mt, String cmnd_ms, MultipartBody.Part hd){
    addSubscribe(mRestData.getListMovies(sdt,idDV,cmnd_mt,cmnd_ms)
            .doOnSubscribe(() -> getMvpView().showLoading())
            .doOnCompleted(() -> getMvpView().hideLoading())
            .subscribe(new DefaultObserver<Movies>() {
              @Override
              public void onError(Throwable e) {
                e.printStackTrace();
                getMvpView().showError("1");
              }

              @Override
              public void onNext(Movies upanh) {
                try {
                  if(upanh.getError()==0) {
                    //getMvpView().uploadOK();
                  }else
                    getMvpView().listMovies(null);
                }catch (Exception e){
                  e.printStackTrace();
                }
              }
            }));
  }
}
