package com.acuteksolutions.uhotel.mvp.model.movies;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Product extends RealmObject{
    private RealmList<Item> items;
    private PurchaseInfo purchaseInfo;

    public Product() {
    }

    public Product(RealmList<Item> items, PurchaseInfo purchaseInfo) {
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
