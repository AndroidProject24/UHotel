package com.acuteksolutions.uhotel.data.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by toan.it
 * Date: 25/05/2016
 */
@Singleton
public class RestData {
  private final RestApi mRestApi;
  @Inject
  public RestData(RestApi restApi) {
    mRestApi = restApi;
  }

  //Movies
  public Observable<Movies> getListMovies(String search, String kho, String dau, String dang) {
    return mRestApi.getKhoso(search,kho,dau,dang)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
 /* public Observable<Movies> getLoadmore(String url) {
    return mRestApi.getLoadmore(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
  public Observable<List<Dangsim>> getDangSim(String noibat) {
    return mRestApi.getDangSim(noibat)
            .map(data->data.data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
  //Login
  public Observable<Login> getLogin(String email, String password, String shopId) {
    return mRestApi.getLogin(email,password,shopId)
            .map(data->data.data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
  public Observable<ResponseBody> forgotPassword(String email,String shopId) {
    return mRestApi.forgotPassword(email,shopId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
  //Register
  public Observable<ResponseBody> checkEmail(String email, String shopId) {
    return mRestApi.checkEmail(email,shopId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
  public Observable<Register> getRegister(String email, String password, String shopId) {
    return mRestApi.getRegister(email,password,shopId)
            .map(data->data.data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
  ////UPLOAD
  public Observable<Upanh> uploadTraTruoc(RequestBody sdt, RequestBody dichvu, MultipartBody.Part mt, MultipartBody.Part ms, MultipartBody.Part hd) {
    return mRestApi.postImageCanhanTratruoc(sdt,dichvu,mt,ms,hd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<Upanh> uploadTraSau(String sdt, String dichvu, RequestBody cmnd_mt, RequestBody cmnd_ms, RequestBody hd_mt, RequestBody hd_ms, RequestBody hd) {
    return mRestApi.postImageCanhanTrasau(sdt,dichvu,cmnd_mt,cmnd_ms,hd_mt,hd_ms,hd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<Upanh> uploadDoanhnghiep(String sdt, String dichvu, RequestBody cmnd_mt, RequestBody cmnd_ms, RequestBody hd_mt, RequestBody hd_ms, RequestBody hd,RequestBody gpkd) {
    return mRestApi.postImageDoanhnghiep(sdt,dichvu,cmnd_mt,cmnd_ms,hd_mt,hd_ms,hd,gpkd)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }
  //Theloai
  public Observable<List<Theloai>> getData(int IDtheloai) {
    return mRestApi.getThutuc(IDtheloai)
            .map(data->data.data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }*/
}
