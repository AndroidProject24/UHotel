package com.acuteksolutions.uhotel.mvp.model.data;

public class Price {
    private String id;
    private double price;
    private String priceType;
    private String symbol;
    private String billingType;
    private String rentalPeriodUnit;
    private long rentalPeriodValue;

    public Price(String id, double price, String priceType, String symbol, String billingType, String rentalPeriodUnit, long rentalPeriodValue) {
        this.id = id;
        this.price = price;
        this.priceType = priceType;
        this.symbol = symbol;
        this.billingType = billingType;
        this.rentalPeriodUnit = rentalPeriodUnit;
        this.rentalPeriodValue = rentalPeriodValue;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceType() {
        return priceType;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getBillingType() {
        return billingType;
    }

    public String getRentalPeriodUnit() {
        return rentalPeriodUnit;
    }


    public long getRentalPeriodValue() {
        return rentalPeriodValue;
    }
}
