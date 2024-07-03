package com.example.onlineshop.service;

import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import com.example.onlineshop.repositories.ProductRepository;
import com.example.onlineshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    // Method to add a product to the user's cart
    public void addToCart(Long productId, User user) {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + user.getId()));

        Product product = productService.findProductById(productId);

        managedUser.getCart().add(product); // Add product to the user's cart
        userRepository.save(managedUser); // Save the updated user entity
    }

    public List<Product> getProductsInCart(User user) {
        return user.getCart();
    }

}
