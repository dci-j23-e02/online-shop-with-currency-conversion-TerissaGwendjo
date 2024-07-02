package com.example.onlineshop.config;

import com.example.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // a Spring Security annotation used to enable web security in a Spring application.
public class SecurityConfig {
    @Bean
    // Annotates this method as a bean that Spring should manage
    public UserDetailsService userDetailsService(UserService userService) {
        return userService;
        // Return the UserService (which implements UserDetailService) as the UserDetailsService
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //allows us to define the chain of security filters
        // This method configures the HttpSecurity object, which allows customization of security settings for HTTP requests.

        http

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // Permit all requests to the home page and static resources
                        .requestMatchers("/signup", "/verify", "/currency-converter").permitAll()
                        .requestMatchers( "/assign-admin","/admin-home", "/products/add", "/products/update*", "/products/delete*").hasRole("ADMIN")
                        // Require authentication for any other request
                        .anyRequest().authenticated()

                )
                .formLogin(formLogin -> formLogin // first page is to be the login page

                        .loginPage("/login") // Specifies the login page URL

                        // Redirect to the home page ("/") after successful login, always redirecting even if the user was previously navigating elsewhere
                        .defaultSuccessUrl("/", true)
                        .permitAll() // Allow everyone to see the login page
                )

                .logout(logout -> logout
                        .logoutUrl("/logout") // Specifies the logout page URL
                        // Redirect to the login page with a logout parameter after a successful logout
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONNID") // Deletes the JSESSIONNID cookie upon logout
                        .invalidateHttpSession(true) // Invalidates the HTTP session after logout to prevent session fixation attacks.
                        .permitAll()
                );
        // Builds and returns the SecurityFilterChain
        return http.build();

    }

    @Autowired
    // Configures the global authentication manager
    public void configureGlobal(
            AuthenticationManagerBuilder auth, // Builder for creating an AuthenticationManager
            UserDetailsService userDetailsService // Service to load user-specific data
    ) throws Exception {
        // This method can throw various exceptions, such as:
        // - ConfigurationException: If there's an error configuring the AuthenticationManagerBuilder
        // - UsernameNotFoundException: If the UserDetailsService cannot find a user by the given username

        auth
                .userDetailsService(userDetailsService) // Uses the provided UserDetailsService to load user details
                .passwordEncoder(new BCryptPasswordEncoder()); // Sets the password encoder to BCrypt for encoding and matching passwords

    }

}
