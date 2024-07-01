package com.example.onlineshop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "entities")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    //Constructors
    public Role() {
    }
    public Role(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }
    public Role(Long id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    //Getters and setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getRoles().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getRoles().remove(this);
    }
}
