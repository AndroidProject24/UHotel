package com.acuteksolutions.uhotel.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {
    private static SharedPreferences mPref;
    private static final String PREF_FILE_NAME = "PREF_APP_NAME";
    private static final String PREF_USER = "PREF_USER_LOGIN";
    @Inject
    public PreferencesHelper(Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }
    public void clear() {
        mPref.edit().clear().apply();
    }

    public void putJsonLogin(Login login) {
        mPref.edit().putString(PREF_USER, new Gson().toJson(login)).apply();
    }

    @Nullable
    public Login getJsonLogin() {
        String data = mPref.getString(PREF_USER, null);
        if (data != null) {
            return new Gson().fromJson(data, Login.class);
        }
        return null;
    }
}
