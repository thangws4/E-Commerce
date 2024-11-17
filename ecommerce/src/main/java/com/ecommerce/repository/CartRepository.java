package com.ecommerce.repository;

import com.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByCustomer_CustomerId(Integer customerId);
}
