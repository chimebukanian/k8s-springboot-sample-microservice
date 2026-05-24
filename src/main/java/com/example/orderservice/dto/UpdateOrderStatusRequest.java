package com.example.orderservice.dto;

import com.example.orderservice.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request to update order status")
public class UpdateOrderStatusRequest {

    @NotNull(message = "Status cannot be null")
    @Schema(description = "New order status", example = "PAID")
    private OrderStatus status;
}

