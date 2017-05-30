package com.acuteksolutions.uhotel.mvp.model.movies;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Category extends RealmObject {
    @PrimaryKey
    private String id;
    private String title;
    private RealmList<Product> product;

    public Category() {

    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", product=" + product +
                '}';
    }

    public Category(String id, String title, RealmList<Product> product) {
        this.id = id;
        this.title = title;
        this.product = product;
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

    public Category update(RealmList<Product> product) {
        this.product = product;
        return this;
    }
}
