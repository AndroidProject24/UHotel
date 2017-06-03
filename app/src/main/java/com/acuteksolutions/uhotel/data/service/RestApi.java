package com.acuteksolutions.uhotel.data.service;

import com.acuteksolutions.uhotel.mvp.model.JsonObject;
import com.acuteksolutions.uhotel.mvp.model.JsonString;
import com.acuteksolutions.uhotel.mvp.model.login.Login;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Toan.IT
 * Date: 28/05/2016
 */

public interface RestApi {
    String BASE_URL = "http://lv-api.acuteksolutions.com/cxf/ws/messagebus/rest/";

    /*Login*/
    @POST("authorize/Beesmart/AuthorizeService/{mac}/{pin}")
    Observable<JsonObject<Login>> getLogin(@Path("mac") String mac, @Path("pin") String pin,
                                           @Query("region") String region_name, @Query("operator") String operator_name);

    /*Movies*/
    @GET("request/{mac}/Beesmart")
    Observable<JsonString<String>> getPathMovies(@Path("mac") String mac, @Query("path") String path);

    /*LiveTV*/
    @GET("request/{mac}/Beesmart")
    Observable<JsonString<String>> getChannel(@Path("mac") String mac, @Query("path") String path);

    @GET("request/{mac}/Beesmart")
    Observable<JsonString<String>> getTVProgram(@Path("mac") String mac, @Query("path") String path);

  /*Pin*/

    @POST("check/Beesmart/PinCodeService/{mac}/{profileId}/authorize")
    Observable<JsonString<Boolean>> verifyPin(@Path("mac") String mac, @Path("profileId") String profileUid,
                                              @Query("parentalPin") String pin);

    @POST("check/Beesmart/PinCodeService/{mac}/{profileId}/changepin")
    Observable<JsonString<Boolean>> changePin(@Path("mac") String mac, @Path("profileId") String profileUid,
                                              @Query("data") String new_pin, @Query("parentalPin") String old_pin);

    @POST("check/Beesmart/PinCodeService/{mac}/{profileId}/update")
    Observable<JsonString<Boolean>> saveSetting(@Path("mac") String mac, @Path("profileId") String profileUid,
                                                @Query("data") String data, @Query("parentalPin") String pin);

    @GET("request/{mac}/Beesmart")
    Observable<JsonString<String>> getLinkStream(@Path("mac") String mac, @Query("path") String path);
}
