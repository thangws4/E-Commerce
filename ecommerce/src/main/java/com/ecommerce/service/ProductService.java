package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategoryId(Integer categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    public List<Product> getProductsByShopId(Integer shopId) {
        return productRepository.findByShop_ShopId(shopId);
    }

    public Page<Product> getAllProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product createProductForShop(Integer shopId, Product product) {
        product.getShop().setShopId(shopId);
        return productRepository.save(product);
    }

    public Product updateProductForShop(Integer shopId, Integer productId, Product productDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (!product.getShop().getShopId().equals(shopId)) {
            throw new RuntimeException("Product does not belong to the specified shop");
        }

        product.setProductName(productDetails.getProductName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setCategory(productDetails.getCategory());
        return productRepository.save(product);
    }

    public void deleteProductForShop(Integer shopId, Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (!product.getShop().getShopId().equals(shopId)) {
            throw new RuntimeException("Product does not belong to the specified shop");
        }

        productRepository.delete(product);
    }
}
