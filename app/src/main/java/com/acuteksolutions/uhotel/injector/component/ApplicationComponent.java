package com.acuteksolutions.uhotel.injector.component;

import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.rxjava.RxBus;
import com.acuteksolutions.uhotel.data.service.RestData;
import com.acuteksolutions.uhotel.injector.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {
    RestData mRestData();
    RxBus mRxBus();
    PreferencesHelper mPreferencesHelper();
}