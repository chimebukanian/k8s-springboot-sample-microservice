package com.example.orderservice.dto;

import com.example.orderservice.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Order response DTO")
public class OrderResponse {

    @Schema(description = "Order ID", example = "1")
    private Long id;

    @Schema(description = "Customer name", example = "John Doe")
    private String customerName;

    @Schema(description = "Product name", example = "Laptop")
    private String productName;

    @Schema(description = "Quantity", example = "2")
    private Integer quantity;

    @Schema(description = "Unit price", example = "500.00")
    private BigDecimal price;

    @Schema(description = "Total amount", example = "1000.00")
    private BigDecimal totalAmount;

    @Schema(description = "Order status", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}

