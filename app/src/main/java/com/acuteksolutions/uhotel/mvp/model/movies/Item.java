package com.acuteksolutions.uhotel.mvp.model.movies;

import io.realm.RealmObject;

/**
 * Created by Toan.IT on 5/30/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class Item extends RealmObject{
    public Item() {
    }

    public Item(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    private String item;
}
