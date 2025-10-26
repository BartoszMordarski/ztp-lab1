package com.example.ztp_lab1.repository;

import com.example.ztp_lab1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
