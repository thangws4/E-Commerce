package com.ecommerce.repository;

import com.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart_CartId(Integer cartId);
    CartItem findByCart_CartIdAndProduct_ProductId(Integer cartId, Integer productId);
}
