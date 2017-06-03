package com.acuteksolutions.uhotel.mvp.presenter;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.repository.Repository;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.livetv.TVInfo;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.LiveTvView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
                .subscribeWith(new DefaultObserver<List<Channel>>() {
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

    public void getProgram(List<Channel> channelList,Date date) {
        addSubscribe(mRepository.getProgram(channelList,date)
                .subscribeWith(new DefaultObserver<List<TVInfo>>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().hideLoading();
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<TVInfo> list) {
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

    public void getDataRightNow(List<TVInfo> tvInfoList){
        addSubscribe(Observable.just(tvInfoList)
                .subscribeOn(Schedulers.newThread())
                .map((Function<List<TVInfo>, List<TVInfo>>) tvInfos -> {
                    if(tvInfos ==null)
                        return new ArrayList<>();
                    List<TVInfo> listNow=new ArrayList<>();
                    for (TVInfo tvInfo: tvInfos){
                        if (tvInfo.isPlaying())
                            listNow.add(tvInfo);
                    }
                    return listNow;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DefaultObserver<List<TVInfo>>(){
                    @Override
                    public void onNext(List<TVInfo> tvInfoList) {
                        //Logger.e(tvInfoList.toString());
                        getMvpView().getDataRightNow(tvInfoList);
                    }
                }));
    }

    public void getDataComingUp(List<TVInfo> tvInfoList){
        addSubscribe(Observable.just(tvInfoList)
                .subscribeOn(Schedulers.newThread())
                .map(tvInfos -> {
                    List<TVInfo> listNow=new ArrayList<>();
                    boolean isFirst=false;
                    for (TVInfo tvInfo:tvInfoList){
                        if(isFirst) {
                            listNow.add(tvInfo);
                            isFirst=false;
                        }
                        if (tvInfo.isPlaying()) {
                            isFirst = true;
                        }
                    }
                    return listNow;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DefaultObserver<List<TVInfo>>(){
                    @Override
                    public void onNext(List<TVInfo> tvInfoList) {
                       // Logger.e(tvInfoList.toString());
                        getMvpView().getDataComingUp(tvInfoList);
                    }
                }));
    }

    public PreferencesHelper getPreferencesHelper(){
        return mPreferencesHelper;
    }
}
