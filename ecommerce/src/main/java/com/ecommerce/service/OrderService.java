package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderDetail;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.OrderDetailRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrderForCustomer(Integer customerId, Order order, List<OrderDetail> orderDetails) {
        order.setOrderDate(new Date());
        double totalAmount = 0;

        // Save the order to get the orderId
        Order savedOrder = orderRepository.save(order);

        for (OrderDetail detail : orderDetails) {
            Product product = productRepository.findById(detail.getProduct().getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            detail.setOrder(savedOrder);
            detail.setPrice(product.getPrice() * detail.getQuantity());
            totalAmount += detail.getPrice();

            orderDetailRepository.save(detail);
        }

        savedOrder.setTotal(totalAmount);
        return orderRepository.save(savedOrder);
    }

    public Optional<Order> getOrderById(Integer orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.findByCustomer_CustomerId(customerId);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrder_OrderId(orderId);
    }

    public void deleteOrderForCustomer(Integer customerId, Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getCustomer().getCustomerId().equals(customerId)) {
            throw new RuntimeException("Order does not belong to the specified customer");
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderId(orderId);
        orderDetailRepository.deleteAll(orderDetails);
        orderRepository.deleteById(orderId);
    }

    public Order updateOrderForCustomer(Integer customerId, Integer orderId, Order updatedOrder) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!order.getCustomer().getCustomerId().equals(customerId)) {
            throw new RuntimeException("Order does not belong to the specified customer");
        }

        order.setShipAddress(updatedOrder.getShipAddress());
        order.setShipDate(updatedOrder.getShipDate());
        order.setVoucher(updatedOrder.getVoucher());

        return orderRepository.save(order);
    }
}
