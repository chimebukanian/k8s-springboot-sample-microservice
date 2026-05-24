package com.example.orderservice.service;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.event.OrderCreatedEvent;
import com.example.orderservice.event.OrderProducer;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "orders-list", key = "#pageable")
    public Page<Order> getOrders(Pageable pageable) {
        log.info("Fetching orders with pagination: {}", pageable);
        return orderRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "order", key = "#id")
    public Order getOrderById(Long id) {
        log.info("Fetching order with id: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional
    @CacheEvict(value = {"orders-list", "order"}, allEntries = true)
    public Order createOrder(CreateOrderRequest request) {
        log.info("Creating order for customer: {}", request.getCustomerName());
        
        Order order = orderMapper.toEntity(request);
        Order savedOrder = orderRepository.save(order);
        
        log.info("Order created with id: {}", savedOrder.getId());
        
        // Publish event
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getProductName()
        );
        orderProducer.send(event);
        log.info("Published order-created event for order id: {}", savedOrder.getId());
        
        return savedOrder;
    }

    @Transactional
    @CacheEvict(value = {"orders-list", "order"}, allEntries = true)
    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        log.info("Updating order {} status to: {}", id, newStatus);
        
        Order order = getOrderById(id);
        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);
        
        log.info("Order {} status updated successfully", id);
        return updated;
    }

    @Transactional
    @CacheEvict(value = {"orders-list", "order"}, allEntries = true)
    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);
        
        Order order = getOrderById(id);
        orderRepository.delete(order);
        
        log.info("Order {} deleted successfully", id);
    }

    @Transactional(readOnly = true)
    public Page<Order> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        log.info("Fetching orders with status: {}", status);
        return orderRepository.findByStatus(status, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Order> searchByCustomerName(String customerName, Pageable pageable) {
        log.info("Searching orders by customer name: {}", customerName);
        return orderRepository.searchByCustomerName(customerName, pageable);
    }
}