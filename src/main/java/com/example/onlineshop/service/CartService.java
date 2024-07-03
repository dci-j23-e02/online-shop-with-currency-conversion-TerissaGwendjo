package com.example.onlineshop.service;

import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;

import java.util.List;

public interface CartService {
    void addToCart (Long productId, User user);
    List<Product>getProductInCart(User user);
}
