package com.example.onlineshop.controllers;

import com.example.onlineshop.models.Product;
import com.example.onlineshop.models.User;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
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
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addProduct(Product product,  Principal principal){
        User user  = userService.findByUsername(principal.getName());
        product.setUser(user);
        productService.saveProduct(product);
        return "redirect:/products";
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
    @PostMapping("/deleteById")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProductById(
            @RequestParam Long id
    ){
        productService.deleteProductById(id);
        return "redirect:/products";
    }


}
