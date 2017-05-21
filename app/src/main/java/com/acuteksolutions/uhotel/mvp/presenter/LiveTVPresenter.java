package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.repository.Repository;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.livetv.Program;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.LiveTvView;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class LiveTVPresenter extends BasePresenter<LiveTvView> {
    private Repository mRepository;
    private PreferencesHelper mPreferencesHelper;
    @Inject LiveTVPresenter(Repository restData, PreferencesHelper preferencesHelper){
        this.mRepository=restData;
        this.mPreferencesHelper=preferencesHelper;
    }

    public void getAllChannel() {
        getMvpView().showLoading();
        addSubscribe(mRepository.getAllChannel()
                .subscribe(new DefaultObserver<List<Channel>>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().hideLoading();
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Channel> list) {
                        try {
                            getMvpView().hideLoading();
                            if(list!=null && !list.isEmpty()) {
                                getMvpView().listAllChannel(list);
                            }else
                                getMvpView().showEmty();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }));
    }

    public void getProgram(long programID,Date date) {
        addSubscribe(mRepository.getProgram(programID,date)
                .subscribe(new DefaultObserver<List<Program>>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().hideLoading();
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Program> list) {
                        try {
                            getMvpView().hideLoading();
                            if(list!=null && !list.isEmpty()) {
                                getMvpView().getProgram(list);
                            }else
                                getMvpView().showEmty();
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
