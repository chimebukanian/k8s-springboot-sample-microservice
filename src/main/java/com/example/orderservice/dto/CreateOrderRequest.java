package com.example.orderservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to create a new order")
public class CreateOrderRequest {

    @NotBlank(message = "Customer name cannot be blank")
    @Schema(description = "Name of the customer", example = "John Doe")
    private String customerName;

    @NotBlank(message = "Product name cannot be blank")
    @Schema(description = "Name of the product", example = "Laptop")
    private String productName;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be greater than 0")
    @Schema(description = "Product quantity", example = "2")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @Schema(description = "Unit price", example = "500.00")
    private BigDecimal price;
}

