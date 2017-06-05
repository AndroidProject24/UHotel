package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.repository.Repository;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;
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
  private Repository mRepository;
  private PreferencesHelper mPreferencesHelper;
  @Inject
  MoviesPresenter(Repository mRepository, PreferencesHelper preferencesHelper){
    this.mRepository=mRepository;
    this.mPreferencesHelper=preferencesHelper;
  }

  public void getCategory() {
    getMvpView().showLoading();
    addSubscribe(mRepository.getCategory()
            .subscribeWith(new DefaultObserver<List<Category>>() {
              @Override
              public void onError(Throwable e) {
                e.printStackTrace();
                getMvpView().hideLoading();
                getMvpView().showError(e.getMessage());
              }

              @Override
              public void onNext(List<Category> list) {
                try {
                  if(list!=null && list.size()>0) {
                    getMvpView().listCategory(list);
                  }else
                    getMvpView().showEmty();
                }catch (Exception e){
                  e.printStackTrace();
                }
              }
            }));
  }

  public void getListMovies(String catID) {
    addSubscribe(mRepository.getListMovies(catID)
            .subscribeWith(new DefaultObserver<List<VODInfo>>() {
              @Override
              public void onError(Throwable e) {
                e.printStackTrace();
                getMvpView().hideLoading();
                getMvpView().showError(e.getMessage());
              }

              @Override
              public void onNext(List<VODInfo> list) {
                try {
                  getMvpView().hideLoading();
                  if(list!=null && list.size()>0) {
                    getMvpView().listMovies(list);
                  }else
                    getMvpView().showEmty();
                }catch (Exception e){
                  e.printStackTrace();
                }
              }
            }));
  }

  public void getLinkStream(String title,String channel,String cid) {
    getMvpView().showLoading();
    addSubscribe(mRepository.getLinkStream(cid)
        .subscribeWith(new DefaultObserver<String>() {
          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
            getMvpView().hideLoading();
            getMvpView().showError(e.getMessage());
          }

          @Override
          public void onNext(String linkStream) {
            try {
              getMvpView().hideLoading();
              if(!Preconditions.isEmpty(linkStream)) {
                getMvpView().playStream(title,channel,linkStream);
              }else
                getMvpView().linkStreamError();
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
