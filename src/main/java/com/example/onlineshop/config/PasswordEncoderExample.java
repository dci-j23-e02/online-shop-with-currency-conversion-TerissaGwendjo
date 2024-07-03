package com.example.onlineshop.config;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderExample {
    public static void main(String[] args) {
        String password = "admin"; // desired password to be encoded

        // Create BCryptPasswordEncoder instance
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Encode the password
        String encodedPassword = encoder.encode(password);

        // Print the encoded password (this is what you will store in the database)
        System.out.println("Encoded Password: " + encodedPassword);
    }
}
