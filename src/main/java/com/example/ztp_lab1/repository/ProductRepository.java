package com.example.ztp_lab1.repository;

import com.example.ztp_lab1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductById(Long id);
    boolean existsByName(String name);
}
