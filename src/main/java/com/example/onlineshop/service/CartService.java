package com.example.onlineshop.service;

import com.example.onlineshop.models.Cart;
import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import com.example.onlineshop.repositories.CartRepository;
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
    @Autowired
    private CartRepository cartRepository;

    // Method to add a product to the user's cart
    public void addToCart(Long productId, User user) {
        // Fetch the product by ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));

        // Fetch or create user's cart
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        // Add the product to the cart
        cart.getProducts().add(product);

        // Save the cart to persist changes
        cartRepository.save(cart);
    }

    public List<Product> getProductsInCart(User user) {
        // Retrieve the user entity from the database with its associated cart
        User userWithCart = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Retrieve the products from the cart
        List<Product> productsInCart = userWithCart.getCart().getProducts();

        return productsInCart;
    }

}
