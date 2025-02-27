package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard"); // Set title cho trang
        return "index"; // Trả về tên template "index.html"
    }
}
