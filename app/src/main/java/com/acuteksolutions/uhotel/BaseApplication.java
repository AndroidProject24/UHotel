package com.acuteksolutions.uhotel;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.widget.ImageView;
import com.acuteksolutions.uhotel.injector.component.ApplicationComponent;
import com.acuteksolutions.uhotel.injector.component.DaggerApplicationComponent;
import com.acuteksolutions.uhotel.injector.module.ApplicationModule;
import com.acuteksolutions.uhotel.libs.logger.LogLevel;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.utils.FakeDataUtils;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.letv.sarrsdesktop.blockcanaryex.jrt.BlockCanaryEx;
import com.letv.sarrsdesktop.blockcanaryex.jrt.Config;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
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
  private RefWatcher refWatcher;
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
    //setupTest();
    Realm.init(this);
    initInjector();
    initData();
    initExoPlayer();
    mInstance = this;
  }

  private void initExoPlayer(){
    userAgent = Util.getUserAgent(this, getString(R.string.app_name));
    okHttpClient = new OkHttpClient.Builder().build();
  }

  public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
    return new OkHttpDataSourceFactory(okHttpClient, userAgent, bandwidthMeter);
  }

  private void initInjector(){
    applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }
  private void initData(){
    try {
      FakeDataUtils.initDataRoom(this);
      if((0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE))) {
     /*   StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            .detectLeakedClosableObjects()
            .penaltyLog()
            .penaltyDeath()
            .build());*/
        Logger.init(getString(R.string.app_name));
      }else{
        Logger.init(getString(R.string.app_name)).logLevel(LogLevel.NONE);
      }
      DrawerImageLoader.init(new AbstractDrawerImageLoader() {
        @Override
        public void set(ImageView imageView, Uri uri, Drawable placeholder) {
          Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
        }

        @Override
        public void cancel(ImageView imageView) {
          Glide.clear(imageView);
        }

        @Override
        public Drawable placeholder(Context ctx, String tag) {
          if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
            return DrawerUIUtils.getPlaceHolder(ctx);
          } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
            return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
          } else if ("customUrlItem".equals(tag)) {
            return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
          }
          return super.placeholder(ctx, tag);
        }
      });
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

  public static RefWatcher getRefWatcher(Context context) {
    BaseApplication application = (BaseApplication) context.getApplicationContext();
    return application.refWatcher;
  }
  private void setupTest(){
    if (BuildConfig.DEBUG) {
     // AndroidDevMetrics.initWith(this);
      if(!BlockCanaryEx.isInSamplerProcess(this)) {
        BlockCanaryEx.install(new Config(this));
      }
      if (LeakCanary.isInAnalyzerProcess(this)) {
        // This process is dedicated to LeakCanary for heap analysis.
        // You should not init your app in this process.
        return;
      }
      refWatcher=LeakCanary.install(this);
    }
  }
}
