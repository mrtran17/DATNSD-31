//package com.example.demo.controller;
//
//import com.example.demo.model.*;
//import com.example.demo.service.*;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin/products")
//public class SanPhamController {
//
//    @Autowired
//    private SanPhamService sanPhamService;
//    @Autowired
//    private DanhMucService danhMucService;
//    @Autowired
//    private ThuongHieuService thuongHieuService;
//    @Autowired
//    private ChatLieuService chatLieuService;
//    @Autowired
//    private KieuDangService kieuDangService;
//
//    // Hàm này sẽ chạy trước tất cả các handler method trong controller
//    @ModelAttribute
//    public void addAttributes(Model model) {
//        model.addAttribute("activePage", "products");
//    }
//
//    @GetMapping
//    public String products(Model model) {
//        List<SanPham> productList = sanPhamService.getAllSanPham(); // Lấy danh sách sản phẩm từ service
//        model.addAttribute("productList", productList);
//        return "admin/product-manage";
//    }
//
//    @GetMapping("/add")
//    public String showAddProductForm(Model model) {
//        model.addAttribute("sanPham", new SanPham());
//        // Lấy danh sách cho dropdown
//        List<DanhMuc> danhMucList = danhMucService.getAllDanhMuc();
//        List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
//        List<ChatLieu> chatLieuList = chatLieuService.getAllChatLieu();
//        List<KieuDang> kieuDangList = kieuDangService.getAllKieuDang();
//
//        model.addAttribute("danhMucList", danhMucList);
//        model.addAttribute("thuongHieuList", thuongHieuList);
//        model.addAttribute("chatLieuList", chatLieuList);
//        model.addAttribute("kieuDangList", kieuDangList);
//
//        return "admin/product/addProductForm";
//    }
//
//    @PostMapping("/add")
//    public String addSanPham(@Valid @ModelAttribute("sanPham") SanPham sanPham, BindingResult bindingResult,
//                             Model model, @RequestParam("imageFiles") MultipartFile[] imageFiles,
//                             RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            // Nếu có lỗi validation, trả lại form với lỗi
//            List<DanhMuc> danhMucList = danhMucService.getAllDanhMuc();
//            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
//            List<ChatLieu> chatLieuList = chatLieuService.getAllChatLieu();
//            List<KieuDang> kieuDangList = kieuDangService.getAllKieuDang();
//
//            model.addAttribute("danhMucList", danhMucList);
//            model.addAttribute("thuongHieuList", thuongHieuList);
//            model.addAttribute("chatLieuList", chatLieuList);
//            model.addAttribute("kieuDangList", kieuDangList);
//            return "admin/product/addProductForm";
//        }
//
//        try {
//            sanPhamService.addNewSanPham(sanPham, imageFiles);
//            redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!");
//            return "redirect:/admin/products/add"; // Redirect thành công
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm sản phẩm: " + e.getMessage());
//            // Trả lại form với thông báo lỗi
//            List<DanhMuc> danhMucList = danhMucService.getAllDanhMuc();
//            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
//            List<ChatLieu> chatLieuList = chatLieuService.getAllChatLieu();
//            List<KieuDang> kieuDangList = kieuDangService.getAllKieuDang();
//
//            model.addAttribute("danhMucList", danhMucList);
//            model.addAttribute("thuongHieuList", thuongHieuList);
//            model.addAttribute("chatLieuList", chatLieuList);
//            model.addAttribute("kieuDangList", kieuDangList);
//            return "admin/product/addProductForm";
//        }
//    }
//}