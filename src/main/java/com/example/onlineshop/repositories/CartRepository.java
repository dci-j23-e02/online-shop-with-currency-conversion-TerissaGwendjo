package com.example.onlineshop.repositories;

import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;

import java.util.List;

public interface CartRepository {
    void addToCart (Long productId, User user);
    List<Product>getProductInCart(User user);
}
