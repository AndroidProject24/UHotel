package com.acuteksolutions.uhotel.mvp.model.livetv;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Channel extends RealmObject{
    @PrimaryKey
    private Integer id;
    private String name;
    private Integer number;
    private String type;
    private boolean hd;
    private boolean broadcasting;
    private long activated;
    private long deactivated;
    private boolean programRecordable;
    private boolean timeshift;
    private boolean pauseAndResume;
    private boolean instantRecordable;
    private boolean voidEpgPopup;
    private Integer ageRating;
    private String icon;
    private String poster;
    private boolean localRecordable;
    private Integer previewDuration;
    private RealmList<Stream> streams;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHd() {
        return hd;
    }

    public void setHd(boolean hd) {
        this.hd = hd;
    }

    public boolean isBroadcasting() {
        return broadcasting;
    }

    public void setBroadcasting(boolean broadcasting) {
        this.broadcasting = broadcasting;
    }

    public long getActivated() {
        return activated;
    }

    public void setActivated(long activated) {
        this.activated = activated;
    }

    public long getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(long deactivated) {
        this.deactivated = deactivated;
    }

    public boolean isProgramRecordable() {
        return programRecordable;
    }

    public void setProgramRecordable(boolean programRecordable) {
        this.programRecordable = programRecordable;
    }

    public boolean isTimeshift() {
        return timeshift;
    }

    public void setTimeshift(boolean timeshift) {
        this.timeshift = timeshift;
    }

    public boolean isPauseAndResume() {
        return pauseAndResume;
    }

    public void setPauseAndResume(boolean pauseAndResume) {
        this.pauseAndResume = pauseAndResume;
    }

    public boolean isInstantRecordable() {
        return instantRecordable;
    }

    public void setInstantRecordable(boolean instantRecordable) {
        this.instantRecordable = instantRecordable;
    }

    public boolean isVoidEpgPopup() {
        return voidEpgPopup;
    }

    public void setVoidEpgPopup(boolean voidEpgPopup) {
        this.voidEpgPopup = voidEpgPopup;
    }

    public Integer getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(Integer ageRating) {
        this.ageRating = ageRating;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public boolean isLocalRecordable() {
        return localRecordable;
    }

    public void setLocalRecordable(boolean localRecordable) {
        this.localRecordable = localRecordable;
    }

    public Integer getPreviewDuration() {
        return previewDuration;
    }

    public void setPreviewDuration(Integer previewDuration) {
        this.previewDuration = previewDuration;
    }

    public RealmList<Stream> getStreams() {
        return streams;
    }

    public void setStreams(RealmList<Stream> streams) {
        this.streams = streams;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "activated=" + activated +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", type='" + type + '\'' +
                ", hd=" + hd +
                ", broadcasting=" + broadcasting +
                ", deactivated=" + deactivated +
                ", programRecordable=" + programRecordable +
                ", timeshift=" + timeshift +
                ", pauseAndResume=" + pauseAndResume +
                ", instantRecordable=" + instantRecordable +
                ", voidEpgPopup=" + voidEpgPopup +
                ", ageRating=" + ageRating +
                ", icon='" + icon + '\'' +
                ", poster='" + poster + '\'' +
                ", localRecordable=" + localRecordable +
                ", previewDuration=" + previewDuration +
                ", streams=" + streams +
                '}';
    }

}
