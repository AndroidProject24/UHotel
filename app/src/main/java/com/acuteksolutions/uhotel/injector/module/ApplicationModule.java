package com.acuteksolutions.uhotel.injector.module;

import com.acuteksolutions.uhotel.BaseApplication;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.rxjava.RxBus;
import com.acuteksolutions.uhotel.data.service.RestApi;
import com.acuteksolutions.uhotel.data.service.RestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

  private final BaseApplication application;

  public ApplicationModule(BaseApplication application) {
    this.application = application;
  }
  @Provides
  @Singleton
  BaseApplication provideApplication() {
    return application;
  }

  @Provides
  @Singleton
  RestApi mRestApi() {
    return RestClient.sRestClient();
  }

  @Provides
  @Singleton
  RxBus mRxBus() {
    return new RxBus();
  }

  @Provides
  @Singleton
  PreferencesHelper mPreferencesHelper() {
    return new PreferencesHelper(application);
  }
}