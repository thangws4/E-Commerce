package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByShop_ShopId(Integer shopId); // Updated method to reference shopId properly
    List<Product> findByCategory_CategoryId(Integer categoryId); // Correct reference to categoryId
    Page<Product> findAll(Pageable pageable);
}
