package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable Integer categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/shop/{shopId}")
    public List<Product> getProductsByShopId(@PathVariable Integer shopId) {
        return productService.getProductsByShopId(shopId);
    }

    @PostMapping("/shop/{shopId}")
    public ResponseEntity<Product> createProductForShop(
            @PathVariable Integer shopId,
            @RequestBody Product product,
            Authentication authentication) {
        // Ensure the authenticated user is adding a product to their own shop
        Product createdProduct = productService.createProductForShop(shopId, product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/shop/{shopId}/{productId}")
    public ResponseEntity<Product> updateProductForShop(
            @PathVariable Integer shopId,
            @PathVariable Integer productId,
            @RequestBody Product productDetails,
            Authentication authentication) {
        // Ensure the authenticated user is updating a product of their own shop
        Product updatedProduct = productService.updateProductForShop(shopId, productId, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/shop/{shopId}/{productId}")
    public ResponseEntity<Void> deleteProductForShop(
            @PathVariable Integer shopId,
            @PathVariable Integer productId,
            Authentication authentication) {
        // Ensure the authenticated user is deleting a product from their own shop
        productService.deleteProductForShop(shopId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/page")
    public Page<Product> getAllProductsPaginated(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProductsPaginated(PageRequest.of(page, size));
    }
}
