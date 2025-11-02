package com.example.ztp_lab1.repository;

import com.example.ztp_lab1.model.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {

    List<ProductHistory> findByProductIdOrderByChangedAtDesc(Long productId);

    List<ProductHistory> findAllByOrderByChangedAtDesc();
}
