package com.acuteksolutions.uhotel.mvp.model.movies;

import io.realm.RealmObject;

/**
 * Created by Toan.IT on 5/30/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class DetailsItem extends RealmObject{
    public DetailsItem() {
    }

    public DetailsItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    private String item;
}
