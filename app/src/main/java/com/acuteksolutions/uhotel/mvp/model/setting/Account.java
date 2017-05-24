package com.acuteksolutions.uhotel.mvp.model.setting;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Toan.IT on 5/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class Account implements MultiItemEntity {
    public static final int ACCOUNT = 1;
    public static final int ACCOUNT_NOTIFY = 2;
    private String name;
    private String value;
    private int itemType;
    private int imaResId;

    public Account(String name, String value, int imaResId, int itemType) {
        this.imaResId = imaResId;
        this.name = name;
        this.value = value;
        this.itemType=itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getImaResId() {
        return imaResId;
    }

    public void setImaResId(int imaResId) {
        this.imaResId = imaResId;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
