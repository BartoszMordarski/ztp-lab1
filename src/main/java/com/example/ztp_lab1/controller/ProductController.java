package com.example.ztp_lab1.controller;

import com.example.ztp_lab1.dto.AddProductDto;
import com.example.ztp_lab1.dto.ProductDto;
import com.example.ztp_lab1.model.Product;
import com.example.ztp_lab1.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    private ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    private ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    private ResponseEntity<AddProductDto> addProduct(@Valid @RequestBody AddProductDto productDto) {
        AddProductDto createdProduct = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }



}
