package com.example.onlineshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    // Specifies that this field is the primary key in the database table.
    @Id
    // Generates a unique identifier using a database sequence.
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false) // Indicates that this field is mapped to a column in the database table and cannot have null values
    private boolean verified = false;

    @ManyToMany(fetch = FetchType.EAGER) // Annotation indicating a many-to-many relationship between user and roles
    @JoinTable(
            name = "user_roles", // Specifies the name of the join table

            // Specifies the join column for the user entity
            // (the foreign key column that references the user entity).
            joinColumns = @JoinColumn(name = "user_id"),

            // Specifies the inverse join column for the role entity
            // (the foreign key column that references the role entity).
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    // A set to hold the roles associated with the user,
    // initialized as an empty HashSet
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    //Constructors

    public User() {
    }

    public User(String username, String email, String password, boolean verified, Set<Role> roles, Cart cart) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.roles = roles;
        this.cart = cart;
    }

    public User(Long id, String username, String email, String password, boolean verified, Set<Role> roles, Cart cart) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.roles = roles;
        this.cart = cart;
    }

    //Getters and setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isVerified() {
        return verified;
    }
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

}
