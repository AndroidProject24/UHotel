package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.repository.Repository;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.PinView;
import javax.inject.Inject;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class PinPresenter extends BasePresenter<PinView> {
    private Repository mRepository;
    private PreferencesHelper mPreferencesHelper;
    @Inject PinPresenter(Repository restData, PreferencesHelper preferencesHelper){
        this.mRepository=restData;
        this.mPreferencesHelper=preferencesHelper;
    }

    public void verifyPin(String pin) {
        getMvpView().showLoading();
        addSubscribe(mRepository.verifyPin(pin)
                .subscribeWith(new DefaultObserver<Boolean>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().hideLoading();
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        getMvpView().hideLoading();
                        getMvpView().verifyPin(aBoolean);
                    }
                }));
    }

    public void changePin(String pinNew,String pinOld) {
        getMvpView().showLoading();
        addSubscribe(mRepository.changePin(pinNew,pinOld)
                .subscribeWith(new DefaultObserver<Boolean>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().hideLoading();
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        getMvpView().hideLoading();
                        getMvpView().changePin(aBoolean);
                    }
                }));
    }

    public void saveSetting(String data,String pin) {
        getMvpView().showLoading();
        addSubscribe(mRepository.saveSetting(data,pin)
                .subscribeWith(new DefaultObserver<Boolean>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().hideLoading();
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        getMvpView().hideLoading();
                        getMvpView().saveSetting(aBoolean);
                    }
                }));
    }
    public PreferencesHelper getPreferencesHelper(){
        return mPreferencesHelper;
    }
}
