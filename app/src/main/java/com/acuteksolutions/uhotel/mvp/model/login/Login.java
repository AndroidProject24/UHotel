package com.acuteksolutions.uhotel.mvp.model.login;

import com.google.gson.Gson;

/**
 * Created by Toan.IT
 * Date: 12/06/2016
 */

public class Login {

    /**
     * subscriberUid : ab6b48c2-e401-4913-8354-281a340291b0
     * profileUid : 82d4b107-aaf8-4a90-81fd-46abc267281d
     * name : 1007
     * gender : M
     * languageId : 1
     * parentalRating : 0
     * autoSubtitlesEnabled : false
     * autoAudioEnabled : false
     * tvRecommendationsEnabled : false
     * vodRecommendationsEnabled : false
     * sipPhoneNotificationsEnabled : false
     * setting : {}
     * userId : 43
     * regionId : 1
     */

    private String subscriberUid;
    private String profileUid;
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
    private int userId;
    private int regionId;

    public String getSubscriberUid() {
        return subscriberUid;
    }

    public void setSubscriberUid(String subscriberUid) {
        this.subscriberUid = subscriberUid;
    }

    public String getProfileUid() {
        return profileUid;
    }

    public void setProfileUid(String profileUid) {
        this.profileUid = profileUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getParentalRating() {
        return parentalRating;
    }

    public void setParentalRating(int parentalRating) {
        this.parentalRating = parentalRating;
    }

    public boolean isAutoSubtitlesEnabled() {
        return autoSubtitlesEnabled;
    }

    public void setAutoSubtitlesEnabled(boolean autoSubtitlesEnabled) {
        this.autoSubtitlesEnabled = autoSubtitlesEnabled;
    }

    public boolean isAutoAudioEnabled() {
        return autoAudioEnabled;
    }

    public void setAutoAudioEnabled(boolean autoAudioEnabled) {
        this.autoAudioEnabled = autoAudioEnabled;
    }

    public boolean isTvRecommendationsEnabled() {
        return tvRecommendationsEnabled;
    }

    public void setTvRecommendationsEnabled(boolean tvRecommendationsEnabled) {
        this.tvRecommendationsEnabled = tvRecommendationsEnabled;
    }

    public boolean isVodRecommendationsEnabled() {
        return vodRecommendationsEnabled;
    }

    public void setVodRecommendationsEnabled(boolean vodRecommendationsEnabled) {
        this.vodRecommendationsEnabled = vodRecommendationsEnabled;
    }

    public boolean isSipPhoneNotificationsEnabled() {
        return sipPhoneNotificationsEnabled;
    }

    public void setSipPhoneNotificationsEnabled(boolean sipPhoneNotificationsEnabled) {
        this.sipPhoneNotificationsEnabled = sipPhoneNotificationsEnabled;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }


    @Override public String toString() {
        return "Login{"
            + "subscriberUid='"
            + subscriberUid
            + '\''
            + ", profileUid='"
            + profileUid
            + '\''
            + ", name='"
            + name
            + '\''
            + ", gender='"
            + gender
            + '\''
            + ", languageId="
            + languageId
            + ", parentalRating="
            + parentalRating
            + ", autoSubtitlesEnabled="
            + autoSubtitlesEnabled
            + ", autoAudioEnabled="
            + autoAudioEnabled
            + ", tvRecommendationsEnabled="
            + tvRecommendationsEnabled
            + ", vodRecommendationsEnabled="
            + vodRecommendationsEnabled
            + ", sipPhoneNotificationsEnabled="
            + sipPhoneNotificationsEnabled
            + ", setting='"
            + setting
            + '\''
            + ", userId="
            + userId
            + ", regionId="
            + regionId
            + '}';
    }


    public SettingInfo getSettingObject() {
        try {
            String result = setting.replace("\\", "");
            return new Gson().fromJson(result, SettingInfo.class);
        } catch (Exception exp) {
            return new SettingInfo();
        }
    }

    public SettingInfo settingObject;

}
