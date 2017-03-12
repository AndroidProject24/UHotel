package com.acuteksolutions.uhotel.mvp.model.data;

import java.util.List;

public class Product {
    private List<String> items;

    @Override
    public String toString() {
        return "Product{" +
                "items=" + items +
                ", purchaseInfo=" + purchaseInfo +
                '}';
    }

    private PurchaseInfo purchaseInfo;

    public Product(List<String> items, PurchaseInfo purchaseInfo) {
        this.items = items;
        this.purchaseInfo = purchaseInfo;
    }

    public List<String> getItems() {
        return items;
    }

    public PurchaseInfo getPurchaseInfo() {
        return purchaseInfo;
    }
}
