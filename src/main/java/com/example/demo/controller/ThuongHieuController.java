package com.example.demo.controller;

import com.example.demo.model.ThuongHieu;
import com.example.demo.service.ThuongHieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/thuong-hieu")
public class ThuongHieuController {

    @Autowired
    private ThuongHieuService thuongHieuService;

    @GetMapping
    public String viewThuongHieuPage(Model model,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "5") int size,
                                     @RequestParam(name = "keyword", required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ThuongHieu> thuongHieuPage = thuongHieuService.getAllThuongHieu(pageable, keyword);

        model.addAttribute("thuongHieuPage", thuongHieuPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword); // Giữ lại keyword khi phân trang
        // Thêm đối tượng ThuongHieu rỗng vào model để form có thể bind
        model.addAttribute("thuongHieu", new ThuongHieu());
        return "admin/thuoc-tinh/thuong-hieu";
    }

    @PostMapping("/add")
    public String addThuongHieu(@Valid @ModelAttribute("thuongHieu") ThuongHieu thuongHieu, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("thuongHieu", thuongHieu); // Giữ lại dữ liệu đã nhập
            return "admin/thuoc-tinh/thuong-hieu"; // Đường dẫn view đã sửa, bỏ /index
        }
        try {
            thuongHieuService.createThuongHieu(thuongHieu);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm thương hiệu thành công!");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage()); // Hiển thị lỗi trùng tên hoặc lỗi khác
            model.addAttribute("thuongHieu", thuongHieu); // Giữ lại dữ liệu đã nhập
            return "admin/thuoc-tinh/thuong-hieu"; // Đường dẫn view đã sửa, bỏ /index
        }
        return "redirect:/thuong-hieu"; // Redirect về trang danh sách
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        ThuongHieu thuongHieu = thuongHieuService.getThuongHieuById(id);
        if (thuongHieu == null) {
            return "error/404"; // Trang lỗi 404 nếu không tìm thấy
        }
        model.addAttribute("thuongHieu", thuongHieu);
        model.addAttribute("isEdit", true); // Cờ để phân biệt form thêm và sửa
        return "admin/thuoc-tinh/thuong-hieu"; // Đường dẫn view đã sửa, bỏ /index
    }


    @PostMapping("/update/{id}")
    public String updateThuongHieu(@PathVariable("id") Integer id, @Valid @ModelAttribute("thuongHieu") ThuongHieu thuongHieu, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("thuongHieu", thuongHieu);
            model.addAttribute("isEdit", true);
            return "admin/thuoc-tinh/thuong-hieu"; // Đường dẫn view đã sửa, bỏ /index
        }
        try {
            thuongHieuService.updateThuongHieu(id, thuongHieu);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thương hiệu thành công!");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("thuongHieu", thuongHieu);
            model.addAttribute("isEdit", true);
            return "admin/thuoc-tinh/thuong-hieu"; // Đường dẫn view đã sửa, bỏ /index
        }
        return "redirect:/thuong-hieu"; // Redirect về trang danh sách
    }


    @GetMapping("/delete/{id}")
    public String deleteThuongHieu(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            thuongHieuService.deleteThuongHieu(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa thương hiệu thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage()); // Truyền lỗi ra view
        }
        return "redirect:/thuong-hieu";
    }
}