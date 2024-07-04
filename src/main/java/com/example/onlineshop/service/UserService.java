package com.example.onlineshop.service;

import com.example.onlineshop.models.Cart;
import com.example.onlineshop.models.Role;
import com.example.onlineshop.models.User;
import com.example.onlineshop.models.VerificationToken;
import com.example.onlineshop.repositories.RoleRepository;
import com.example.onlineshop.repositories.UserRepository;
import com.example.onlineshop.repositories.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    JavaMailSender mailSender;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by username
        User user = userRepository.findByUsername(username);
        System.out.println(user.isVerified());
        System.out.println(user.getUsername());
        // If user is not found
        if (user == null) {
            // Throw UsernameNotFoundException
            throw new UsernameNotFoundException("User not found");
        }
        //Log user details:
        System.out.println("User found " + user.getUsername() + " , verified:" + user.isVerified() + " Roles: " + user.getRoles());



        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername()) // Set the username
                .password(user.getPassword()) // Set the password
                .authorities(
                        user.getRoles() // Retrieves the list of roles from the user object
                                .stream() // Converts the list of roles into a stream for processing
                                .map(role -> new SimpleGrantedAuthority(role.getName()))  // take each role and create a new simple granted authority object
                                .collect(Collectors.toList())) // collects roles to authorities; Collects the stream of SimpleGrantedAuthority objects back into a list
                .accountLocked(!user.isVerified()) // if the account is verified, then this statement is false so account won't be locked if account isn't verified, then statement is true and account will be suspended
                .build();
    }


    @Transactional
    /*
    * Manages the transactional behavior of this method, ensuring that all operations within it either complete
    * successfully or are rolled back in case of an error or exception.
    */
    public boolean saveUser(User user) {
        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set up roles for the user
        Set<Role> roles = new HashSet<>();

        // Find the user role by name from the repository
        Role userRole = roleRepository.findByName("ROLE_USER");

        // If ROLE_USER doesn't exist, create and save it
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }

        // Add the user role to the roles set
        roles.add(userRole);

        // Set the roles for the user
        user.setRoles(roles);

        // Ensure bidirectional relationship: add the user to the userRole's users set
        userRole.addUser(user);

        try {
            // Save the user to the userRepository
            userRepository.save(user);

            // Send verification email to the user
            sendVerificationEmail(user);
        } catch (MailException e) {
            // Catch MailException if email sending fails
            // Log the exception message (optional)
            System.out.println("Failed to send verification email:" + e.getMessage());

            // Return false indicating failure
            return false;
        }

        // Return true indicating successful user save
        return true;
    }


    private void sendVerificationEmail(User user) throws  MailException{
        // Generate a unique token using UUID
        String token = UUID.randomUUID().toString();

        // Store the generated token for the user in the database
        createOrUpdateVerificationToken(user, token);

        // Get the recipient's email address from the user object
        String recipientAddress = user.getEmail();

        // Set the subject line of the email
        String subject = "Email Verification";

        // Construct the verification URL using the generated token
        String confirmationUrl = "http://localhost:6969/verify?token=" + token;

        // Create the email message body with the verification URL
        String message = "Please click the link below to verify your email address:\n" + confirmationUrl;

        // Create a SimpleMailMessage object to compose the email
        SimpleMailMessage email = new SimpleMailMessage();

        // Set the recipient's email address
        email.setTo(recipientAddress);

        // Set the subject of the email
        email.setSubject(subject);

        // Set the body message of the email
        email.setText(message);

        // Send the email using the configured mailSender
        mailSender.send(email);
    }


    // Creates or updates a verification token for the given user.
    public void createOrUpdateVerificationToken(User user, String token) {
        // Find existing verification token by user's username
        VerificationToken verificationToken = tokenRepository.findByUsername(user.getUsername());

        // If no token exists, create a new one
        if (verificationToken == null) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
        }

        // Update the token value
        verificationToken.setToken(token);

        // Set the expiry date of the token to 1 day from now
        verificationToken.setExpiryDate(LocalDateTime.now().plusDays(1));

        // Save the verification token in the repository
        tokenRepository.save(verificationToken);
    }

    // Retrieves a verification token based on the token string.
    public VerificationToken getVerificationToken(String token) {
        // Find and return the verification token from the repository
        return tokenRepository.findByToken(token);
    }


    // Verifies a user based on the provided verification token.
    public void verifyUser(String token) {
        // Retrieve the verification token based on the provided token string
        VerificationToken verificationToken = getVerificationToken(token);

        // Check if the verification token exists and is not expired
        if (verificationToken != null // Token exists
                && verificationToken.getExpiryDate().isAfter(LocalDateTime.now()) // Token is not expired
        ) {
            // Get the user associated with the verification token
            User user = verificationToken.getUser();

            // Set the user's verified status to true
            user.setVerified(true);

            // Save the updated user information in the userRepository
            userRepository.save(user);

            // Optional: Uncomment if you want to delete the verification token after verification
            // tokenRepository.delete(verificationToken);

            // Log verification success
            System.out.println("User verified: " + user.getUsername());
        } else {
            // Log verification failure (invalid or expired token)
            System.out.println("Verification token is invalid or expired");
        }
    }

    @Transactional
    public void updateUserRoles(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //Assign role to users
    public void assignRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                user.getRoles().add(role);
                userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Role not found");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


}
