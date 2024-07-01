package com.example.onlineshop.repositories;

import com.example.onlineshop.models.Role;
import com.example.onlineshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.roles=?2 WHERE u.username=?1")
    void updateUserRoles(String username, Set<Role> roles);

}
