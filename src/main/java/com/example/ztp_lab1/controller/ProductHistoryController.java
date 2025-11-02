package com.example.ztp_lab1.controller;

import com.example.ztp_lab1.dto.ProductHistoryDto;
import com.example.ztp_lab1.model.ProductHistory;
import com.example.ztp_lab1.service.ProductHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductHistoryController {

    private final ProductHistoryService historyService;

    @GetMapping("/{id}/history")
    public ResponseEntity<List<ProductHistoryDto>> getProductHistory(@PathVariable Long id) {
        List<ProductHistory> history = historyService.getProductHistory(id);
        List<ProductHistoryDto> dtos = history.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ProductHistoryDto>> getAllHistory() {
        List<ProductHistory> history = historyService.getAllHistory();
        List<ProductHistoryDto> dtos = history.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private ProductHistoryDto mapToDto(ProductHistory history) {
        return ProductHistoryDto.builder()
                .id(history.getId())
                .productId(history.getProductId())
                .name(history.getName())
                .price(history.getPrice())
                .quantity(history.getQuantity())
                .category(history.getCategory())
                .action(history.getAction())
                .changedAt(history.getChangedAt())
                .build();
    }
}