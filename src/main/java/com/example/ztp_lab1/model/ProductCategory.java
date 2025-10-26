package com.example.ztp_lab1.model;

public enum ProductCategory {
    ELECTRONICS(50, 50000),
    BOOKS(5, 500),
    CLOTHES(10, 5000);

    private final int minPrice;
    private final int maxPrice;

    ProductCategory(int minPrice, int maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }
}
