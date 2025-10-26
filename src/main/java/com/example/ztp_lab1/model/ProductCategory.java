package com.example.ztp_lab1.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
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

    public boolean isPriceInRange(BigDecimal price) {
        return price.compareTo(BigDecimal.valueOf(minPrice)) >= 0 &&
               price.compareTo(BigDecimal.valueOf(maxPrice)) <= 0;
    }
}
