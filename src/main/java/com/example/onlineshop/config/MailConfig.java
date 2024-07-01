package com.example.onlineshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

// Indicates that this class contains one or more @Bean methods and may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
@Configuration
public class MailConfig {
    @Bean  // Indicates that this method produces a bean to be managed by the Spring container.
    public JavaMailSender javaMailSender(){
        // Create an instance of JavaMailSenderImpl, which is an implementation of the JavaMailSender interface.
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Set the host for the mail server.
        mailSender.setHost("smtp.gmail.com");
        // Set the port for the mail server.
        mailSender.setPort(587);
        // Set the username for the mail server authentication.
        mailSender.setUsername("gweter4@gmail.com");
        // Set the password for the mail server authentication.
        mailSender.setPassword("fatu hauh xkas weaq");

        // Get the mail properties and set additional properties.
        Properties props = mailSender.getJavaMailProperties();
        // Specify the protocol for the mail session.
        props.put("mail.transport.protocol", "smtp");
        // Enable SMTP authentication.
        props.put("mail.smtp.auth", "true");
        // Enable STARTTLS to secure the connection.
        props.put("mail.smtp.starttls.enable", "true");
        // Enable debug mode to print out detailed logs.
        props.put("mail.debug", "true");

        // Return the configured JavaMailSender instance.
        return mailSender;
    }
}
