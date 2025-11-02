package com.example.ztp_lab1.dto;

import com.example.ztp_lab1.model.HistoryAction;
import com.example.ztp_lab1.model.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistoryDto {
    private Long id;
    private Long productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private ProductCategory category;
    private HistoryAction action;
    private LocalDateTime changedAt;
}