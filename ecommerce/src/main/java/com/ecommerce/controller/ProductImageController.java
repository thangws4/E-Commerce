package com.ecommerce.controller;

import com.ecommerce.entity.ProductImage;
import com.ecommerce.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productImages")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping
    public List<ProductImage> getAllProductImages() {
        return productImageService.getAllProductImages();
    }

    @GetMapping("/product/{productID}")
    public List<ProductImage> getImagesByProductId(@PathVariable Integer productID) {
        return productImageService.getImagesByProductId(productID);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductImage> getProductImageById(@PathVariable Integer id) {
        Optional<ProductImage> productImage = productImageService.getProductImageById(id);
        return productImage.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductImage createProductImage(@RequestBody ProductImage productImage) {
        return productImageService.createProductImage(productImage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductImage> updateProductImage(@PathVariable Integer id, @RequestBody ProductImage imageDetails) {
        ProductImage updatedImage = productImageService.updateProductImage(id, imageDetails);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Integer id) {
        productImageService.deleteProductImage(id);
        return ResponseEntity.noContent().build();
    }
}