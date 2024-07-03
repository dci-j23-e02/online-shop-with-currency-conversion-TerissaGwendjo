package com.example.onlineshop.controllers;

import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listProducts(Model model, Principal principal ){
        User user = userService.findByUsername(principal.getName());
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    //Methods accessible only by admins
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addProductForm(Model model){
        model.addAttribute("product", new Product());
        return "add-product";
    }
    // Endpoint to add a new product (restricted to admins)
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addProduct(@ModelAttribute("product") Product product, Authentication authentication) {
        User adminUser = userService.findByUsername(authentication.getName());

        // Associate the product with the admin user
        product.setUser(adminUser);

        // Save the product
        productService.saveProduct(product);

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}") // Maps GET requests for /expenses/edit/{id} to this method
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id); // Finds the product by ID
        model.addAttribute("product", product); // Adds the product to the model
        model.addAttribute("users", userService.findAllUsers()); // Adds a list of all users to the model
        return "edit-product"; // Returns the view name "edit-expense"
    }
    @PostMapping("/update/{id}") // Maps POST requests for /expenses/update/{id} to this method
    @PreAuthorize("hasRole('ADMIN')")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productService.updateProduct(id, product); // Updates the expense
        return "redirect:/products"; // Redirects to the list of expenses
    }


    @PostMapping("/updateAmount")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateProductAmount(
            @RequestParam Double amount,
            @RequestParam Long id
    ){
        productService.updateProductAmount(amount, id);
        return "redirect:/products";
    }
    @PostMapping("/deleteByCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public  String deleteProductByCategory(
            @RequestParam String category
    ){
        productService.deleteProductByCategory(category);
        return "redirect:/products";
    }
    @PostMapping("/updateDescription")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateProductDescription(
            @RequestParam String description,
            @RequestParam Long id
    ){
        productService.updateProductDescription(description, id);
        return "redirect:/products";
    }
    @PostMapping("/updateCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateProductCategory(
            @RequestParam String category,
            @RequestParam Long id
    ){
        productService.updateProductCategory(category, id);
        return "redirect:/products";
    }
    @PostMapping("/deleteByDateRange")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProductsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            Principal principal
    ){
        User user = userService.findByUsername(principal.getName());
        productService.deleteProductsByDateRangeAndUser(startDate, endDate, user.getId());
        return "redirect:/products";
    }
    @PostMapping("/updateDate")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateProductDate(
            @RequestParam LocalDate date,
            @RequestParam Long id
    ){
        productService.updateProductDate(date, id);
        return "redirect:/products";
    }
    @PostMapping("/deleteByUser")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProductByUser(
            @RequestParam Long userId

    ) {
        productService.deleteProductByUser(userId);
        return "redirect:/products";
    }
    @PostMapping("/updateMultiple")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateMultipleProducts(
            @RequestParam Double amount,
            @RequestParam List<Long> ids
    ){
        productService.updateMultipleProducts(amount, ids);
        return "redirect:/products";
    }
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProductById(
            @PathVariable Long id
    ){
        productService.deleteProductById(id);
        return "redirect:/products";
    }


}
