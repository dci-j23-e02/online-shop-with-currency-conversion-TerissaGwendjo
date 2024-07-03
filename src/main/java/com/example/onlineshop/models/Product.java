package com.example.onlineshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table (name = "products")
public class Product {
    // Specifies that this field is the primary key in the database table.
    @Id
    // Generates a unique identifier using a database sequence.
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // Many-to-One relationship: Many transactions can belong to one user.
    @ManyToOne
    // Specifies the column name for the foreign key.
    @JoinColumn(name = "user_id")
    private User user;

    // Represents the amount of the product
    private Double amount;

    // Represents the name of the product.
    private String name;

    // Represents a description or note associated with the transaction.
    private String description;

    // Represents the category or type of product
    private String category;

    // Represents the date of the transaction.
    private LocalDate date;

    //Constructor

    public Product() {
    }

    public Product(User user, Double amount, String name, String description, String category, LocalDate date) {
        this.user = user;
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    public Product(Long id, User user, Double amount, String name, String description, String category, LocalDate date) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    //Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
