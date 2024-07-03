package com.example.onlineshop.controllers;

import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import com.example.onlineshop.service.CartService;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Product> cartProducts = cartService.getProductsInCart(user);
        model.addAttribute("products", cartProducts);
        return "cart";
    }
    // Endpoint to show all available products
    @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "cart-products"; // Thymeleaf template for displaying available products
    }

    // Endpoint to add a product to the cart
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        cartService.addToCart(productId, user);
        return "redirect:/cart"; // Redirect to the cart view page
    }

}
