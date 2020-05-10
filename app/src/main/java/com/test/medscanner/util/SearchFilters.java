package com.test.medscanner.util;

public class SearchFilters {
    private int priceFrom;
    private int priceTo;
    private int distance;


    public int getPriceFrom() {
        return priceFrom;

    }

    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    public int getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(int priceTo) {
        this.priceTo = priceTo;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
