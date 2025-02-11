package com.example.demo.controller;

import com.example.demo.model.ThuongHieu;
import com.example.demo.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/thuong-hieu")
public class ThuongHieuController {

    @Autowired
    private ThuongHieuService thuongHieuService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("activePage", "thuongHieu");
        List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
        model.addAttribute("thuongHieuList", thuongHieuList);
        model.addAttribute("thuongHieu", new ThuongHieu());
        model.addAttribute("isEdit", false);
        return "admin/thuoc-tinh/thuong-hieu";
    }

    @PostMapping("/add")
    public String addThuongHieu(@ModelAttribute("thuongHieu") ThuongHieu thuongHieu,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        // Validation không được bỏ trống (thủ công trong Controller)
        if (thuongHieu.getTenThuongHieu() == null || thuongHieu.getTenThuongHieu().trim().isEmpty()) {
            bindingResult.addError(new FieldError("thuongHieu", "tenThuongHieu", "Tên thương hiệu không được để trống"));
            model.addAttribute("activePage", "thuongHieu");
            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
            model.addAttribute("thuongHieuList", thuongHieuList);
            model.addAttribute("isEdit", false); // **THÊM DÒNG NÀY: Đặt isEdit = false khi trả lại form lỗi**
            return "admin/thuoc-tinh/thuong-hieu"; // Trả lại form nếu có lỗi
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "thuongHieu");
            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
            model.addAttribute("thuongHieuList", thuongHieuList);
            model.addAttribute("isEdit", false); // **THÊM DÒNG NÀY: Đặt isEdit = false khi trả lại form lỗi**
            return "admin/thuoc-tinh/thuong-hieu"; // Trả lại form nếu có lỗi
        }

        try {
            thuongHieuService.saveThuongHieu(thuongHieu);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm thương hiệu thành công!");
            return "redirect:/admin/thuong-hieu";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Tên thương hiệu đã tồn tại. Vui lòng chọn tên khác.");
            model.addAttribute("activePage", "thuongHieu");
            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
            model.addAttribute("thuongHieuList", thuongHieuList);
            model.addAttribute("isEdit", false); // **THÊM DÒNG NÀY: Đặt isEdit = false khi trả lại form lỗi**
            return "admin/thuoc-tinh/thuong-hieu"; // Trả lại form nếu trùng tên
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm thương hiệu: " + e.getMessage());
            model.addAttribute("activePage", "thuongHieu");
            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
            model.addAttribute("thuongHieuList", thuongHieuList);
            model.addAttribute("isEdit", false); // **THÊM DÒNG NÀY: Đặt isEdit = false khi trả lại form lỗi**
            return "admin/thuoc-tinh/thuong-hieu"; // Trả lại form nếu có lỗi khác
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        ThuongHieu thuongHieu = thuongHieuService.getThuongHieuById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Thương Hiệu Id:" + id));

        model.addAttribute("thuongHieu", thuongHieu);
        model.addAttribute("activePage", "thuongHieu");
        List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
        model.addAttribute("thuongHieuList", thuongHieuList);
        model.addAttribute("isEdit", true);
        return "admin/thuoc-tinh/thuong-hieu";
    }

    @PostMapping("/update")
    public String updateThuongHieu(@ModelAttribute("thuongHieu") ThuongHieu thuongHieu,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        // Validation không được bỏ trống (thủ công trong Controller)
        if (thuongHieu.getTenThuongHieu() == null || thuongHieu.getTenThuongHieu().trim().isEmpty()) {
            bindingResult.addError(new FieldError("thuongHieu", "tenThuongHieu", "Tên thương hiệu không được để trống"));
            model.addAttribute("activePage", "thuongHieu");
            model.addAttribute("isEdit", true); // **THÊM DÒNG NÀY: Đặt isEdit = true khi trả lại form lỗi**
            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
            model.addAttribute("thuongHieuList", thuongHieuList);
            return "admin/thuoc-tinh/thuong-hieu"; // Trả lại form edit nếu có lỗi
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "thuongHieu");
            model.addAttribute("isEdit", true); // **THÊM DÒNG NÀY: Đặt isEdit = true khi trả lại form lỗi**
            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
            model.addAttribute("thuongHieuList", thuongHieuList);
            return "admin/thuoc-tinh/thuong-hieu"; // Trả lại form edit nếu có lỗi
        }
        try {
            thuongHieuService.updateThuongHieu(thuongHieu);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thương hiệu thành công!");
            return "redirect:/admin/thuong-hieu";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Tên thương hiệu đã tồn tại. Vui lòng chọn tên khác.");
            model.addAttribute("activePage", "thuongHieu");
            model.addAttribute("isEdit", true); // **THÊM DÒNG NÀY: Đặt isEdit = true khi trả lại form lỗi**
            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
            model.addAttribute("thuongHieuList", thuongHieuList);
            return "admin/thuoc-tinh/thuong-hieu"; // Trả lại form edit nếu trùng tên
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật thương hiệu: " + e.getMessage());
            model.addAttribute("activePage", "thuongHieu");
            model.addAttribute("isEdit", true); // **THÊM DÒNG NÀY: Đặt isEdit = true khi trả lại form lỗi**
            List<ThuongHieu> thuongHieuList = thuongHieuService.getAllThuongHieu();
            model.addAttribute("thuongHieuList", thuongHieuList);
            return "admin/thuoc-tinh/thuong-hieu"; // Trả lại form edit nếu có lỗi khác
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteThuongHieu(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes, Model model) {
        try {
            thuongHieuService.deleteThuongHieu(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa thương hiệu thành công!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa thương hiệu vì có sản phẩm đang sử dụng thương hiệu này.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi xóa thương hiệu: " + e.getMessage());
        }
        return "redirect:/admin/thuong-hieu";
    }
}