package com.acuteksolutions.uhotel.data.service;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Toan.IT
 * Date: 28/05/2016
 */

public interface RestApi {
    String BASE_URL = "http://n3t.top/test/api/";

    /*Movies*/
    @GET("timsim")
    Observable<Movies> getKhoso(@Query("search") String search, @Query("kho") String kho, @Query("dau") String dau, @Query("dang") String dang);
/*
    @GET
    Observable<Movies> getLoadmore(@Url String url);

    @GET("dangsim")
    Observable<JsonArray<Dangsim>> getDangSim(@Query("noibat") String noibat);

    *//*LOGIN*//*
    @POST("login")
    Observable<JsonObject<Login>> getLogin(@Query("email") String email, @Query("password") String password, @Query("shop_id") String shop_id);

    @POST("request-password")
    Observable<ResponseBody> forgotPassword(@Query("email") String email, @Query("shop_id") String shop_id);

    *//*REGISTER*//*
    @POST("validate")
    Observable<ResponseBody> checkEmail(@Query("email") String email, @Query("shop_id") String shop_id);

    @GET("register")
    Observable<JsonObject<Register>> getRegister(@Query("email") String email, @Query("password") String password, @Query("shop_id") String shop_id);

    *//*UPANH*//*
    @Multipart
    @POST("upanh/tratruoc")
    Observable<Upanh> postImageCanhanTratruoc(@Part("sdt") RequestBody sdt, @Part("dichvu") RequestBody dichvu,
                                              @Part MultipartBody.Part file1,
                                              @Part MultipartBody.Part file2,
                                              @Part MultipartBody.Part file3);

    @Multipart
    @POST("upanh/trasaucanhan")
    Observable<Upanh> postImageCanhanTrasau(@Part(value = "sdt") String sdt, @Part(value = "dichvu") String dichvu, @Part("cmnd_mt\"; filename=\"image1.png\" ") RequestBody photo1,
                                            @Part("cmnd_ms\"; filename=\"image2.png\" ") RequestBody photo2, @Part("hopdong_mt\"; filename=\"image3.png\" ") RequestBody photo3, @Part("hopdong_ms\"; filename=\"image4.png\" ") RequestBody photo4, @Part("phuluc4\"; filename=\"image5.png\" ") RequestBody photo5);

    @Multipart
    @POST("upanh/trasaudoanhnghiep")
    Observable<Upanh> postImageDoanhnghiep(@Part(value = "sdt") String sdt, @Part(value = "dichvu") String dichvu, @Part("cmnd_mt\"; filename=\"image1.png\" ") RequestBody photo1,
                                           @Part("cmnd_ms\"; filename=\"image2.png\" ") RequestBody photo2, @Part("hopdong_mt\"; filename=\"image3.png\" ") RequestBody photo3, @Part("hopdong_ms\"; filename=\"image4.png\" ") RequestBody photo4,
                                           @Part("phuluc4\"; filename=\"image5.png\" ") RequestBody photo5, @Part("gpkd\"; filename=\"image6.png\" ") RequestBody photo6);
    *//*KHUYEMAI*//*
    @GET("theloai/{IDtheloai}")
    Observable<JsonArray<Theloai>> getThutuc(@Path("IDtheloai") int IDtheloai);*/

}
