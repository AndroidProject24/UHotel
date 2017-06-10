package com.acuteksolutions.uhotel.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.utils.GsonGenericList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesHelper {
    private SharedPreferences mPref;
    private final String PREF_FILE_NAME = "PREF_APP_NAME";
    private final String PREF_USER = "PREF_USER_LOGIN";
    private final String PREF_CHANNEL= "PREF_CHANNEL";
    private final String PREF_TOTAL= "PREF_TOTAL";
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

    public void putRoomTotal(int total) {
        mPref.edit().putInt(PREF_TOTAL, total).apply();
    }

    public int getRoomTotal() {
        return mPref.getInt(PREF_TOTAL,0);
    }

    public void putJsonChannel(List<Channel> channel) {
        mPref.edit().putString(PREF_CHANNEL, new Gson().toJson(channel)).apply();
    }

    @Nullable
    public List<Channel> getJsonChannel() {
        String data = mPref.getString(PREF_CHANNEL, null);
        if (data != null) {
            return new Gson().fromJson(data, new GsonGenericList(Channel.class));
        }
        return null;
    }

    public void putListObject(String key, List<?> listObject) {
        Gson gson = new Gson();
        mPref.edit().putString(key, gson.toJson(listObject)).apply();
    }

    public <T> ArrayList<T> getListObject(String key, Class<T> classType) {
        try {
            String result = mPref.getString(key, "");
            Gson gson = new Gson();
            return gson.fromJson(result, new GsonGenericList<T>(classType));
        } catch (Exception exp) {
            return null;
        }
    }

}
