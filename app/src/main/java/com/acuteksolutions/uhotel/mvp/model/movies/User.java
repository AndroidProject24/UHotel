package com.acuteksolutions.uhotel.mvp.model.movies;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String profileUid;
    private int userId;
    private int regionId;
    private String subscriberUid;
    private String name;
    private String gender;
    private int languageId;
    private int parentalRating;
    private boolean autoSubtitlesEnabled;
    private boolean autoAudioEnabled;
    private boolean tvRecommendationsEnabled;
    private boolean vodRecommendationsEnabled;
    private boolean sipPhoneNotificationsEnabled;
    private String setting;

    public User(String profileUid, int userId, int regionId, String subscriberUid, String name, String gender, int languageId, int parentalRating, boolean autoSubtitlesEnabled, boolean autoAudioEnabled, boolean tvRecommendationsEnabled, boolean vodRecommendationsEnabled, boolean sipPhoneNotificationsEnabled, String setting) {
        this.profileUid = profileUid;
        this.userId = userId;
        this.regionId = regionId;
        this.subscriberUid = subscriberUid;
        this.name = name;
        this.gender = gender;
        this.languageId = languageId;
        this.parentalRating = parentalRating;
        this.autoSubtitlesEnabled = autoSubtitlesEnabled;
        this.autoAudioEnabled = autoAudioEnabled;
        this.tvRecommendationsEnabled = tvRecommendationsEnabled;
        this.vodRecommendationsEnabled = vodRecommendationsEnabled;
        this.sipPhoneNotificationsEnabled = sipPhoneNotificationsEnabled;
        this.setting = setting;
    }

    public String getProfileUid() {
        return profileUid;
    }

    public int getUserId() {
        return userId;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getSubscriberUid() {
        return subscriberUid;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getLanguageId() {
        return languageId;
    }

    public int getParentalRating() {
        return parentalRating;
    }

    public boolean isAutoSubtitlesEnabled() {
        return autoSubtitlesEnabled;
    }

    public boolean isAutoAudioEnabled() {
        return autoAudioEnabled;
    }

    public boolean isTvRecommendationsEnabled() {
        return tvRecommendationsEnabled;
    }

    public boolean isVodRecommendationsEnabled() {
        return vodRecommendationsEnabled;
    }

    public boolean isSipPhoneNotificationsEnabled() {
        return sipPhoneNotificationsEnabled;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public JSONObject getSettingAsJsonObject() throws JSONException {
        return new JSONObject(setting);
    }
}
