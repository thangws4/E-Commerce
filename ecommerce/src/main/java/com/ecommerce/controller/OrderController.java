package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderDetail;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Order> createOrderForCustomer(
            @PathVariable Integer customerId,
            @RequestBody Order order,
            @RequestBody List<OrderDetail> orderDetails) {
        Order createdOrder = orderService.createOrderForCustomer(customerId, order, orderDetails);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable Integer customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }


    @PutMapping("/customer/{customerId}/{orderId}")
    public ResponseEntity<Order> updateOrderForCustomer(
            @PathVariable Integer customerId,
            @PathVariable Integer orderId,
            @RequestBody Order updatedOrder) {
        Order updated = orderService.updateOrderForCustomer(customerId, orderId, updatedOrder);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/customer/{customerId}/{orderId}")
    public ResponseEntity<Void> deleteOrderForCustomer(
            @PathVariable Integer customerId,
            @PathVariable Integer orderId) {
        orderService.deleteOrderForCustomer(customerId, orderId);
        return ResponseEntity.noContent().build();
    }
}
