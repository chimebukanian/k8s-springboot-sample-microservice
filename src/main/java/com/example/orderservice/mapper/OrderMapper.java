package com.example.orderservice.mapper;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(CreateOrderRequest request) {
        if (request == null || request.getCustomerName() == null) {
            throw new IllegalArgumentException("Customer name is required");
        }
        return Order.builder()
                .customerName(request.getCustomerName())
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .build();
    }

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .productName(order.getProductName())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}

