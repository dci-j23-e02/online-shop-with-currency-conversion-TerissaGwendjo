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

    // Method to add a product to the user's cart
    public void addToCart(String name, String description, Double amount,
                          String category, LocalDate date, User user) {
        // Create a new Product entity
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setAmount(amount);
        product.setCategory(category);
        product.setDate(date);

        // Add the product to the user's cart
        user.getCart().add(product);
    }

    public List<Product> getProductsInCart(User user) {
        return user.getCart();
    }

}
