package com.example.onlineshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Indicates that this class contains one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
@Configuration
// This class is a configuration class for password encoding
public class PasswordEncoderConfig {
    @Bean // The @Bean annotation tells Spring that this method returns a bean to be managed by the Spring container
    public BCryptPasswordEncoder passwordEncoder(){ // This method defines a bean of type BCryptPasswordEncoder

        // Returns a new instance of BCryptPasswordEncoder, which is used for hashing passwords
        return  new BCryptPasswordEncoder();
    }
}
