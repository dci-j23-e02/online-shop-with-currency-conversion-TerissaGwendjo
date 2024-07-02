package com.example.onlineshop.service;

import com.example.onlineshop.models.Cart;
import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import com.example.onlineshop.repositories.CartRepository;
import com.example.onlineshop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;

    @Transactional
    public Product saveProduct(Product product) {
        if(product == null){
            throw new IllegalArgumentException("invalid product object");
        }
        return  productRepository.save(product);
    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    @Transactional
    public void updateProductAmount(Double amount, Long id){
        productRepository.updateProductAmount(amount, id);
    }
    @Transactional
    public void deleteProductByCategory(String category){
        productRepository.deleteProductByCategory(category);
    }
    @Transactional
    public void updateProductDescription(String description, Long id){
        productRepository.updateProductDescription(description, id);
    }
    @Transactional
    public void updateProductCategory(String category, Long id){
        productRepository.updateProductCategory(category, id);
    }
    @Transactional
    public void deleteProductByDateRange(LocalDate startDate, LocalDate endDate){
        // validate : startDate is before endDate (optionally)
        productRepository.deleteProductsByDateRange(startDate, endDate);
    }
    @Transactional
    public void updateProductDate(LocalDate date, Long id){
        productRepository.updateProductDate(date, id);
    }
    @Transactional
    public void deleteProductByUser(Long userId){
        productRepository.deleteProductByUser(userId);
    }
    @Transactional
    public void updateProductUser(User user, Long id){
        productRepository.updateProductUser(user, id);
    }
    @Transactional
    public void updateMultipleProducts(Double amount, List<Long> ids){
        productRepository.updateMultipleProducts(amount, ids);
    }

    @Transactional
    public void deleteProductById(Long id){
        productRepository.deleteProductById(id);
    }
    public List<Product> findAllProductsByUser(Long userId){
        return productRepository.findAllProductsByUser(userId);
    }

    @Transactional
    public  void deleteProductsByDateRangeAndUser(LocalDate startDate, LocalDate endDate, Long userId){
        productRepository.deleteProductsByDateRangeAndUser(startDate, endDate, userId);
    }


    public void addToCart(Long productId, User user) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setProducts(new HashSet<>());
        }
        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    public Set<Product> listAllProductsByUser(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            return cart.getProducts();
        }
        return Set.of(); // Return an empty set if the cart is null
    }
}
