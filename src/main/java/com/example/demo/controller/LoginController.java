package com.example.demo.controller;

import com.example.demo.model.KhachHang;
import com.example.demo.service.KhachHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if(session.getAttribute("loggedInUser") != null){
            return "redirect:/home";
        }
        return "login"; // Tên của file HTML
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession session, Model model) {
        KhachHang khachHang = khachHangService.login(email, password);
        if (khachHang != null) {
            session.setAttribute("loggedInUser", khachHang);
            return "redirect:/home"; // Chuyển hướng đến trang chủ
        } else {
            model.addAttribute("errorMessage", "Thông tin đăng nhập không đúng.");
            return "login"; // Hiển thị lại trang login với thông báo lỗi
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login"; // Chuyển hướng về trang login
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model){
        KhachHang loggedInUser = (KhachHang) session.getAttribute("loggedInUser");
        if(loggedInUser == null){
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        return "home";
    }


    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("khachHang", new KhachHang()); // Initialize the KhachHang object
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("khachHang") KhachHang khachHang,
                               BindingResult result,
                               RedirectAttributes redirectAttributes, Model model) {

        String registerResult = khachHangService.dangKyKhachHang(khachHang, result);

        if(result.hasErrors()){
            return "register";
        }


        if (registerResult.equals("Đăng ký thành công")) {
            redirectAttributes.addFlashAttribute("message", registerResult);
            return "redirect:/login";
        }else{
            model.addAttribute("message", registerResult);
            return "register";
        }

    }
}
