package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.data.service.RestData;
import com.acuteksolutions.uhotel.mvp.model.data.Category;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.MoviesView;
import com.acuteksolutions.uhotel.utils.Preconditions;
import java.util.List;
import javax.inject.Inject;

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
  public void getCategory() {
    addSubscribe(mRestData.getCategory(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId())
            .doOnSubscribe(() -> getMvpView().showLoading())
            .doOnCompleted(() -> getMvpView().hideLoading())
            .subscribe(new DefaultObserver<List<Category>>() {
              @Override
              public void onError(Throwable e) {
                e.printStackTrace();
                getMvpView().showError(e.getMessage());
              }

              @Override
              public void onNext(List<Category> list) {
                try {
                  if(!list.isEmpty()) {
                    getMvpView().listCategory(list);
                  }else
                    getMvpView().listMovies(null);
                }catch (Exception e){
                  e.printStackTrace();
                }
              }
            }));
  }

  public void getMoviesDetails(String catID) {
    addSubscribe(mRestData.getMoviesDetails(String.valueOf(Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin()).getRegionId()),catID)
            .doOnSubscribe(() -> getMvpView().showLoading())
            .doOnCompleted(() -> getMvpView().hideLoading())
            .subscribe(new DefaultObserver<List<VODInfo>>() {
              @Override
              public void onError(Throwable e) {
                e.printStackTrace();
                getMvpView().showError(e.getMessage());
              }

              @Override
              public void onNext(List<VODInfo> list) {
                try {
                  if(!list.isEmpty()) {
                    getMvpView().listMovies(list);
                  }else
                    getMvpView().listMovies(null);
                }catch (Exception e){
                  e.printStackTrace();
                }
              }
            }));
  }
  public PreferencesHelper getPreferencesHelper(){
    return mPreferencesHelper;
  }
}
