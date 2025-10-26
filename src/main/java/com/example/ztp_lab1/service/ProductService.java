package com.example.ztp_lab1.service;

import com.example.ztp_lab1.dto.AddProductDto;
import com.example.ztp_lab1.dto.ProductDto;
import com.example.ztp_lab1.model.Product;
import com.example.ztp_lab1.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return productRepository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product not found with id=%d", id)));
    }

    public void deleteProductById(Long id) {
        if(!productRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Product not found with id=%d", id));
        }
        productRepository.deleteById(id);
    }

    public AddProductDto addProduct(AddProductDto productDto) {
        if(productRepository.existsByName(productDto.getName())) {
            throw new IllegalArgumentException(
                    String.format("Product with name: %s already exists", productDto.getName())
            );
        }

        if(!productDto.getCategory().isPriceInRange(productDto.getPrice())) {
            throw new IllegalArgumentException(
                    String.format("Price for category %s must be between %d and %d",
                            productDto.getCategory(),
                            productDto.getCategory().getMinPrice(),
                            productDto.getCategory().getMaxPrice()
                    )
            );
        }

        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .category(productDto.getCategory())
                .build();

        product = productRepository.save(product);

        return AddProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .build();
    }
}
