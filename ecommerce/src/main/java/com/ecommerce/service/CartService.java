package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addToCart(Integer customerId, Integer productId, Integer quantity) {
        // Find the cart for the customer, or create a new one if it doesn't exist
        Cart cart = cartRepository.findByCustomer_CustomerId(customerId);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(findCustomerById(customerId)); // Replace with actual customer fetching logic
            cartRepository.save(cart);
        }

        // Find the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the cart already has the product
        CartItem cartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId);

        if (cartItem != null) {
            // If the product is already in the cart, update the quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setPrice(cartItem.getQuantity() * product.getPrice());
        } else {
            // If the product is not in the cart, add a new CartItem
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(quantity * product.getPrice());
        }

        cartItemRepository.save(cartItem);
        return cart;
    }

    // Dummy method for customer retrieval (replace with actual logic)
    private Customer findCustomerById(Integer customerId) {
        // Add the logic to fetch the customer by ID
        return new Customer(); // Placeholder
    }
}