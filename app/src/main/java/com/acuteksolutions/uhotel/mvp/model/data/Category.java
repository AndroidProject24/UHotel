package com.acuteksolutions.uhotel.mvp.model.data;

import java.util.List;

public class Category {
    private String id;

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", product=" + product +
                '}';
    }

    private String title;
    private List<Product> product;

    public Category(String id, String title, List<Product> product) {
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

    public List<Product> getProduct() {
        return product;
    }

    public Category update(List<Product> product) {
        this.product = product;
        return this;
    }
}
