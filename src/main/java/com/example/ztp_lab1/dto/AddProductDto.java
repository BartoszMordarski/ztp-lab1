package com.example.ztp_lab1.dto;

import com.example.ztp_lab1.model.ProductCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDto {
    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 3, max = 20, message = "Product name must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Product name can only contain letters and numbers")
    private String name;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotNull(message = "Category cannot be null")
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
}
