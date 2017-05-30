package com.acuteksolutions.uhotel.mvp.model.movies;

import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Category extends RealmObject {
    @PrimaryKey
    private String id;
    private String title;
    private RealmList<Product> product;
    private RealmList<VODInfo> vodInfos;

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", product=" + Arrays.toString(product.toArray()) +
                ", vodInfos=" + Arrays.toString(vodInfos.toArray()) +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setProduct(RealmList<Product> product) {
        this.product = product;
    }

    public RealmList<Product> getProduct() {
        return product;
    }

    public RealmList<VODInfo> getVodInfos() {
        return vodInfos;
    }

    public void setVodInfos(RealmList<VODInfo> vodInfos) {
        this.vodInfos = vodInfos;
    }
}
