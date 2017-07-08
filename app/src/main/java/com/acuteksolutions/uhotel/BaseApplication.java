package com.acuteksolutions.uhotel;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.acuteksolutions.uhotel.injector.component.ApplicationComponent;
import com.acuteksolutions.uhotel.injector.component.DaggerApplicationComponent;
import com.acuteksolutions.uhotel.injector.module.ApplicationModule;
import com.acuteksolutions.uhotel.libs.logger.LogLevel;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.utils.FakeDataUtils;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import io.realm.Realm;
import okhttp3.OkHttpClient;

/**
 * Created by Toan.IT
 * Date:2016/6/6
 * Time:20:59
 */
public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    private ApplicationComponent applicationComponent;
    //private RefWatcher refWatcher;
    protected String userAgent;
    private OkHttpClient okHttpClient;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupTest();
        Realm.init(this);
        initInjector();
        initData();
        initExoPlayer();
        mInstance = this;
    }

    private void initExoPlayer() {
        userAgent = Util.getUserAgent(this, getString(R.string.app_name));
        okHttpClient = new OkHttpClient.Builder().build();
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new OkHttpDataSourceFactory(okHttpClient, userAgent, bandwidthMeter);
    }

    private void initInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initData() {
        try {
            FakeDataUtils.initDataRoom(this);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            if ((0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE))) {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .build());
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects()
                        .penaltyLog()
                        .penaltyDeath()
                        .build());
                Logger.init(getString(R.string.app_name));
            } else {
                Logger.init(getString(R.string.app_name)).logLevel(LogLevel.NONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

  /*  public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }
*/
    private void setupTest() {
        /*if (BuildConfig.DEBUG) {
            // AndroidDevMetrics.initWith(this);
            BlockCanary.install(this, new AppBlockCanaryContext()).start();
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            refWatcher = LeakCanary.install(this);
        }*/
    }
}
