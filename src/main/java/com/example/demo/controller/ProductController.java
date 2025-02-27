package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("pageTitle", "Trang Sản Phẩm"); // Set title cho trang
        return "products"; // Trả về tên template "products.html"
    }
}
