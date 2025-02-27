package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/mau-sac")
public class MauSacController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("activePage", "mauSac");
        return "admin/thuoc-tinh/mau-sac";
    }
}
