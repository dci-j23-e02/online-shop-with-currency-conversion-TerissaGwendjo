package com.example.onlineshop.repositories;

import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Modifying
    // Modifies the amount of an Expense entity using a custom JPQL query.
    @Query("UPDATE Product  p SET p.amount=?1 WHERE p.id=?2")
    void updateProductAmount(Double amount, Long id);

    @Modifying
    @Query("DELETE FROM Product p WHERE p.category=?1")
    void deleteProductByCategory(String category);

    @Modifying
    @Query("UPDATE Product p SET p.description=?1 WHERE p.id=?2")
    void updateProductDescription(String description, Long id);

    @Modifying
    @Query("UPDATE Product p SET p.category=?1  WHERE p.id=?2")
    void updateProductCategory(String category, Long id);


    @Modifying
    @Query("DELETE FROM Product p WHERE p.date BETWEEN ?1 AND ?2")
    void deleteProductsByDateRange(LocalDate startDate, LocalDate endDate);

    @Modifying
    @Query("UPDATE Product p SET p.date=?1 WHERE p.id=?2")
    void updateProductDate(LocalDate date, Long id);

    @Modifying
    @Query("UPDATE Product p SET p.user =?1 WHERE p.id=?2")
    void updateProductUser(User user, Long id);

    @Modifying
    @Query("DELETE FROM Product e WHERE e.user.id=?1")
    void deleteProductByUser(Long userId);

    @Modifying
    @Query("UPDATE Product p SET p.amount = ?1 WHERE p.id IN ?2")
    void updateMultipleProducts(Double amount, List<Long> ids);

    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = ?1")
    void deleteProductById(Long id);

    @Query("SELECT p FROM  Product p WHERE p.user.id=?1")
    List<Product> findAllProductsByUser(Long userId);


    @Modifying
    @Query("DELETE FROM Product p WHERE p.date BETWEEN ?1 AND ?2 AND p.user.id=?3")
    void deleteProductsByDateRangeAndUser(LocalDate startDate, LocalDate endDate, Long userId);
}