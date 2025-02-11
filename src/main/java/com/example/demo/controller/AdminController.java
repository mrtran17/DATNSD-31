package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("activePage", "index"); // Đặt activePage là "index" cho trang chủ
        return "admin/index";
    }

    @GetMapping("/calendar")
    public String calendar(Model model) {
        model.addAttribute("activePage", "calendar"); // Đặt activePage là "calendar" cho trang lịch
        return "admin/calendar";
    }

//    @GetMapping("/products")
//    public String products(Model model) {
//        model.addAttribute("activePage", "products"); // Đặt activePage là "calendar" cho trang lịch
//        return "admin/product-manage";
//    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("activePage", "dashboard"); // Đặt activePage là "dashboard" cho trang dashboard
        return "admin/dashboard";
    }

    @GetMapping("/profile")
    public String orders(Model model) {
        model.addAttribute("activePage", "profile"); // Đặt activePage là "profile" cho trang hồ sơ
        return "admin/profile";
    }
}