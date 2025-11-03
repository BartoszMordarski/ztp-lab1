package com.example.ztp_lab1.service;

import com.example.ztp_lab1.dto.NewProductDto;
import com.example.ztp_lab1.dto.ProductDto;
import com.example.ztp_lab1.model.HistoryAction;
import com.example.ztp_lab1.model.Product;
import com.example.ztp_lab1.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNameValidator<String> bannedPhraseValidator;
    private final ProductHistoryService historyService;

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

    @Transactional
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Product not found with id=%d", id)
                ));
        historyService.saveHistory(product, HistoryAction.DELETE);
        productRepository.deleteById(id);
    }

    @Transactional
    public NewProductDto addProduct(NewProductDto newProductDto) {
        bannedPhraseValidator.validate(newProductDto.getName());

        if(productRepository.existsByName(newProductDto.getName())) {
            throw new IllegalArgumentException(
                    String.format("Product with name: %s already exists", newProductDto.getName())
            );
        }

        validatePriceForCategory(newProductDto);

        Product product = Product.builder()
                .name(newProductDto.getName())
                .price(newProductDto.getPrice())
                .quantity(newProductDto.getQuantity())
                .category(newProductDto.getCategory())
                .build();

        product = productRepository.save(product);
        historyService.saveHistory(product, HistoryAction.CREATE);

        return NewProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .build();
    }

    @Transactional
    public NewProductDto updateProduct(Long id, NewProductDto newProductDto) {
        bannedPhraseValidator.validate(newProductDto.getName());

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Product not found with id=%d", id)
                ));

        if (!existingProduct.getName().equals(newProductDto.getName()) &&
                productRepository.existsByName(newProductDto.getName())) {
            throw new IllegalArgumentException(
                    String.format("Product with name: %s already exists", newProductDto.getName())
            );
        }

        validatePriceForCategory(newProductDto);

        existingProduct.setName(newProductDto.getName());
        existingProduct.setPrice(newProductDto.getPrice());
        existingProduct.setQuantity(newProductDto.getQuantity());
        existingProduct.setCategory(newProductDto.getCategory());

        Product updatedProduct = productRepository.save(existingProduct);
        historyService.saveHistory(updatedProduct, HistoryAction.UPDATE);

        return NewProductDto.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .price(updatedProduct.getPrice())
                .quantity(updatedProduct.getQuantity())
                .category(updatedProduct.getCategory())
                .build();
    }

    private void validatePriceForCategory(NewProductDto productDto) {
        if (!productDto.getCategory().isPriceInRange(productDto.getPrice())) {
            throw new IllegalArgumentException(
                    String.format("Price for category %s must be between %d and %d",
                            productDto.getCategory(),
                            productDto.getCategory().getMinPrice(),
                            productDto.getCategory().getMaxPrice()
                    )
            );
        }
    }
}
