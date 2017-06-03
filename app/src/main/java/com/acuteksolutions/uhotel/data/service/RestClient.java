package com.acuteksolutions.uhotel.data.service;


import android.util.Log;

import com.acuteksolutions.uhotel.BuildConfig;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by toan.it
 * Date: 28/05/2016
 */
public class RestClient {
    public static RestApi sRestClient() {
        OkHttpClient okHttpClient = makeOkHttpClient(makeLoggingInterceptor());
        return sRestClient(okHttpClient);
    }

    private static RestApi sRestClient(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestApi.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(RestApi.class);
    }

    private static OkHttpClient makeOkHttpClient(LoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    private static LoggingInterceptor makeLoggingInterceptor() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Log.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build();
    }

}