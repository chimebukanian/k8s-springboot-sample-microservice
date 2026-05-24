package com.example.orderservice.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.example.orderservice.event.OrderCreatedEvent;


@Component
@Slf4j
public class OrderConsumer {

    @KafkaListener(topics = "order-created", groupId = "order-service-group")
    public void consumeOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Consumed order-created event: orderId={}",
                event.getOrderId());
//        , customerName={}, totalAmount={}
//        , event.getCustomerName(), event.getTotalAmount()
    }
}

