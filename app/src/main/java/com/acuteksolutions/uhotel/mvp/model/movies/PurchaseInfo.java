package com.acuteksolutions.uhotel.mvp.model.movies;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PurchaseInfo extends RealmObject{
    @PrimaryKey
    private String purchaseGroupId;
    private boolean purchasable;
    private String offerVersion;
    private String type;
    private RealmList<Price> prices;
    private Period purchasePeriod;
    private Period consumptionPeriod;

    public PurchaseInfo() {

    }

    public PurchaseInfo(String purchaseGroupId, boolean purchasable, String offerVersion, String type, RealmList<Price> prices, Period purchasePeriod, Period consumptionPeriod) {
        this.purchaseGroupId = purchaseGroupId;
        this.purchasable = purchasable;
        this.offerVersion = offerVersion;
        this.type = type;
        this.prices = prices;
        this.purchasePeriod = purchasePeriod;
        this.consumptionPeriod = consumptionPeriod;
    }

    public String getPurchaseGroupId() {
        return purchaseGroupId;
    }

    public boolean isPurchasable() {
        return purchasable;
    }

    public String getOfferVersion() {
        return offerVersion;
    }

    public String getType() {
        return type;
    }

    public RealmList<Price> getPrices() {
        return prices;
    }

    public Period getPurchasePeriod() {
        return purchasePeriod;
    }

    public Period getConsumptionPeriod() {
        return consumptionPeriod;
    }
}
