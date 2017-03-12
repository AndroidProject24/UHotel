package com.acuteksolutions.uhotel.mvp.model.data;

import java.util.List;

public class PurchaseInfo {
    private String purchaseGroupId;
    private boolean purchasable;
    private String offerVersion;
    private String type;
    private List<Price> prices;
    private Period purchasePeriod;
    private Period consumptionPeriod;

    public PurchaseInfo(String purchaseGroupId, boolean purchasable, String offerVersion, String type, List<Price> prices, Period purchasePeriod, Period consumptionPeriod) {
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

    public List<Price> getPrices() {
        return prices;
    }

    public Period getPurchasePeriod() {
        return purchasePeriod;
    }

    public Period getConsumptionPeriod() {
        return consumptionPeriod;
    }
}
