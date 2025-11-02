package com.example.ztp_lab1.service;

import com.example.ztp_lab1.model.HistoryAction;
import com.example.ztp_lab1.model.Product;
import com.example.ztp_lab1.model.ProductHistory;
import com.example.ztp_lab1.repository.ProductHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHistoryService {

    private final ProductHistoryRepository historyRepository;

    @Transactional
    public void saveHistory(Product product, HistoryAction action) {
        ProductHistory history = ProductHistory.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .action(action)
                .build();

        historyRepository.save(history);
    }

    public List<ProductHistory> getProductHistory(Long productId) {
        return historyRepository.findByProductIdOrderByChangedAtDesc(productId);
    }

    public List<ProductHistory> getAllHistory() {
        return historyRepository.findAllByOrderByChangedAtDesc();
    }
}