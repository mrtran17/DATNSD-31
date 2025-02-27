package com.example.demo.controller;

import com.example.demo.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khachhang")
public class KhachHangController {

    @Autowired
    KhachHangRepository khachHangRepository;

    @GetMapping("/check-connection")
    public ResponseEntity<String> checkKhachHangConnection() {
        try {
            long count = khachHangRepository.count();
            return ResponseEntity.ok("Kết nối đến bảng KhachHang thành công. Số lượng bản ghi hiện tại: " + count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi kết nối đến bảng KhachHang: " + e.getMessage());
        }
    }
}
