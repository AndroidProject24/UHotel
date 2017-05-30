package com.acuteksolutions.uhotel.mvp.model.movies;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject{
    @PrimaryKey
    private int id;
    private RealmList<Item> items;

    private PurchaseInfo purchaseInfo;

    public Product() {
    }

    public Product(int id,RealmList<Item> items, PurchaseInfo purchaseInfo) {
        this.id=id;
        this.items = items;
        this.purchaseInfo = purchaseInfo;
    }

    public RealmList<Item> getItems() {
        return items;
    }

    public PurchaseInfo getPurchaseInfo() {
        return purchaseInfo;
    }
}
