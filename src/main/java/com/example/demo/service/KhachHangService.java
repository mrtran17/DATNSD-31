package com.example.demo.service;

import com.example.demo.model.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class KhachHangService {
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Autowired
    private KhachHangRepository khachHangRepository;

    public KhachHang login(String email, String password) {
        Optional<KhachHang> khachHangOptional = khachHangRepository.findByEmail(email);
        if (khachHangOptional.isPresent()) {
            KhachHang khachHang = khachHangOptional.get();
            if (password.equals(khachHang.getMatKhau())) {
                return khachHang;
            }
        }
        return null;
    }


    public String dangKyKhachHang(KhachHang khachHang, BindingResult result) {

        if (khachHang.getTen() == null || khachHang.getTen().trim().isEmpty()) {
            result.addError(new FieldError("khachHang", "ten", "Tên không được để trống"));
        }

        if (khachHang.getEmail() == null || khachHang.getEmail().trim().isEmpty()) {
            result.addError(new FieldError("khachHang", "email", "Email không được để trống"));
        } else if (!pattern.matcher(khachHang.getEmail()).matches()) {
            result.addError(new FieldError("khachHang", "email", "Email không đúng định dạng"));
        } else if (khachHangRepository.existsByEmail(khachHang.getEmail())) {
            result.addError(new FieldError("khachHang", "email", "Email này đã được sử dụng."));

        }

        if (khachHang.getSoDienThoai() == null || khachHang.getSoDienThoai().trim().isEmpty()) {
            result.addError(new FieldError("khachHang", "soDienThoai", "Số điện thoại không được để trống"));
        }else if(!Pattern.matches("^\\d{10}$",khachHang.getSoDienThoai())){
            result.addError(new FieldError("khachHang","soDienThoai","Số điện thoại phải có 10 chữ số"));
        } else if (khachHangRepository.existsBySoDienThoai(khachHang.getSoDienThoai())){
            result.addError(new FieldError("khachHang","soDienThoai","Số điện thoại này đã được sử dụng."));
        }

        if (khachHang.getMatKhau() == null || khachHang.getMatKhau().trim().isEmpty()) {
            result.addError(new FieldError("khachHang", "matKhau", "Mật khẩu không được để trống"));
        }else if (khachHang.getMatKhau().trim().length() < 6){
            result.addError(new FieldError("khachHang", "matKhau", "Mật khẩu phải có ít nhất 6 ký tự"));
        }
        if (result.hasErrors()){
            return null;
        }

        // Tạo mã khách hàng
        String maKhachHang = generateMaKhachHang();
        khachHang.setMaKhachHang(maKhachHang);
        khachHang.setTrangThai(true);

        khachHangRepository.save(khachHang);
        return "Đăng ký thành công";
    }


    private String generateMaKhachHang(){
        Optional<KhachHang> khachHangOptional = khachHangRepository.findFirstByOrderByMaKhachHangDesc();
        int nextNumber = 1;
        if(khachHangOptional.isPresent()){
            String maKhachHangHienTai = khachHangOptional.get().getMaKhachHang();
            String numberPart = maKhachHangHienTai.substring(2);
            try{
                nextNumber = Integer.parseInt(numberPart) + 1;
            }catch (NumberFormatException e){
                nextNumber = 1;
            }

        }

        return String.format("KH%04d", nextNumber);
    }
}