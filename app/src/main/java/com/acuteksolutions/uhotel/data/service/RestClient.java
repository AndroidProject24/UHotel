package com.acuteksolutions.uhotel.data.service;


import android.util.Log;

import com.acuteksolutions.uhotel.BuildConfig;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(RestApi.class);
    }

    private static OkHttpClient makeOkHttpClient(LoggingInterceptor httpLoggingInterceptor) {
       // File cacheFile= new File(context.getCacheDir(), Constant.HTTP_CACHE);
       // Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        return new OkHttpClient.Builder()
               // .cache(cache)
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

    //Offline
   /* @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder r = chain.request().newBuilder();
        if (isConnected()) {
            int maxAge = 2 * 60;
            r.addHeader("cache-control", "public, max-age=" + maxAge);
        } else {
            int maxStale = 30 * 24 * 60 * 60; // 30 days
            r.addHeader("cache-control", "public, only-if-cached, max-stale=" + maxStale);
        }

        return chain.proceed(r.build());
    }

    protected boolean isConnected() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }*/
}