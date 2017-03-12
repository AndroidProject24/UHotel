package com.acuteksolutions.uhotel.mvp.model.data;

public class VODInfo implements Comparable<VODInfo> {
    private int purchaseId;
    private String contentInfoUid;
    private Detail detail;
    private int contentId;
    public VODInfo(int purchaseId, String contentInfoUid, Detail detail, int contentId) {
        this.purchaseId = purchaseId;
        this.contentInfoUid = contentInfoUid;
        this.detail = detail;
        this.contentId = contentId;
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

    @Override
    public int compareTo(VODInfo another) {
        return purchaseId - another.getPurchaseId();
    }
}
