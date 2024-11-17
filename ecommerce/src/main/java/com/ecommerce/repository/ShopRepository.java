package com.ecommerce.repository;

import com.ecommerce.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    List<Shop> findByUser_UserId(Integer userId);

}
