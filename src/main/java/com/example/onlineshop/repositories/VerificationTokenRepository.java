package com.example.onlineshop.repositories;

import com.example.onlineshop.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VerificationTokenRepository extends JpaRepository <VerificationToken, Long> {
    VerificationToken findByToken(String token);

    @Query("SELECT v FROM  VerificationToken v WHERE v.user.username=?1")
    VerificationToken findByUsername(String username);
}
