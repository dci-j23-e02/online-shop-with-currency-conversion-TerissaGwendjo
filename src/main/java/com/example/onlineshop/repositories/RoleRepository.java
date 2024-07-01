package com.example.onlineshop.repositories;

import com.example.onlineshop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByName(String name);
}
