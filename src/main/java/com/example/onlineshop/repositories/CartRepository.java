package com.example.onlineshop.repositories;

import com.example.onlineshop.models.Cart;
import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository <Cart, Long> {
    //void addToCart(Long productId, User user);

    //List<Product> getProductInCart(User user);
}
