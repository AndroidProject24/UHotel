package com.acuteksolutions.uhotel.mvp.model.movies;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class VODInfo extends RealmObject{
    @PrimaryKey
    private int purchaseId;
    private String contentInfoUid;
    private Detail detail;
    private int contentId;
    private String categoryID;
    public VODInfo(int purchaseId, String contentInfoUid, Detail detail, int contentId,String categoryID) {
        this.purchaseId = purchaseId;
        this.contentInfoUid = contentInfoUid;
        this.detail = detail;
        this.contentId = contentId;
        this.categoryID=categoryID;
    }

    public VODInfo() {

    }

    @Override
    public String toString() {
        return "VODInfo{" +
                "purchaseId=" + purchaseId +
                ", contentInfoUid='" + contentInfoUid + '\'' +
                ", detail=" + detail +
                ", contentId=" + contentId +
                '}';
    }

    public String getCategoryID() {
        return categoryID;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public String getContentInfoUid() {
        return contentInfoUid;
    }

    public Detail getDetail() {
        return detail;
    }

    public int getContentId() {
        return contentId;
    }
}
