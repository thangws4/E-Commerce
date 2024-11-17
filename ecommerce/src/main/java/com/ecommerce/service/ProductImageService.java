package com.ecommerce.service;

import com.ecommerce.entity.ProductImage;
import com.ecommerce.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }

    public List<ProductImage> getImagesByProductId(Integer productID) {
        return productImageRepository.findByProduct_ProductId(productID);
    }

    public Optional<ProductImage> getProductImageById(Integer id) {
        return productImageRepository.findById(id);
    }

    public ProductImage createProductImage(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public ProductImage updateProductImage(Integer id, ProductImage imageDetails) {
        ProductImage image = productImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Image not found"));
        image.setImageUrl(imageDetails.getImageUrl());
        image.setProduct(imageDetails.getProduct());
        return productImageRepository.save(image);
    }

    public void deleteProductImage(Integer id) {
        ProductImage image = productImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Image not found"));
        productImageRepository.delete(image);
    }
}