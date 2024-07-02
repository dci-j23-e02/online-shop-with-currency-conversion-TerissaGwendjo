package com.example.onlineshop.repositories;

import com.example.onlineshop.models.Cart;
import com.example.onlineshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
