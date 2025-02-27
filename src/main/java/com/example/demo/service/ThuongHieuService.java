package com.example.demo.service;

import com.example.demo.model.ThuongHieu;
import com.example.demo.repository.ThuongHieuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class ThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    public Page<ThuongHieu> getAllThuongHieu(Pageable pageable, String keyword) {
        if (StringUtils.hasText(keyword)) {
            return thuongHieuRepository.findByTenThuongHieuContainingIgnoreCase(keyword, pageable);
        }
        return thuongHieuRepository.findAll(pageable);
    }

    public List<ThuongHieu> getAllThuongHieuForDropdown() { // Lấy tất cả không phân trang cho dropdown lọc
        return thuongHieuRepository.findAll();
    }


    public ThuongHieu getThuongHieuById(Integer id) {
        return thuongHieuRepository.findById(id).orElse(null);
    }

    public ThuongHieu createThuongHieu(ThuongHieu thuongHieu) {
        if (thuongHieuRepository.existsByTenThuongHieu(thuongHieu.getTenThuongHieu())) {
            throw new RuntimeException("Tên thương hiệu đã tồn tại"); // Validation trùng tên
        }
        return thuongHieuRepository.save(thuongHieu);
    }

    public ThuongHieu updateThuongHieu(Integer id, ThuongHieu thuongHieu) {
        ThuongHieu existingThuongHieu = getThuongHieuById(id);
        if (existingThuongHieu == null) {
            throw new RuntimeException("Không tìm thấy thương hiệu với ID: " + id);
        }
        if (!existingThuongHieu.getTenThuongHieu().equalsIgnoreCase(thuongHieu.getTenThuongHieu()) && // Kiểm tra nếu tên thay đổi
                thuongHieuRepository.existsByTenThuongHieuAndThuongHieuIdNot(thuongHieu.getTenThuongHieu(), id)) {
            throw new RuntimeException("Tên thương hiệu đã tồn tại"); // Validation trùng tên khi sửa
        }

        existingThuongHieu.setTenThuongHieu(thuongHieu.getTenThuongHieu());
        existingThuongHieu.setTrangThai(thuongHieu.getTrangThai());
        return thuongHieuRepository.save(existingThuongHieu);
    }

    public void deleteThuongHieu(Integer id) {
        ThuongHieu thuongHieu = getThuongHieuById(id);
        if (thuongHieu == null) {
            throw new RuntimeException("Không tìm thấy thương hiệu với ID: " + id);
        }
        if (thuongHieu.getSanPhams() != null && !thuongHieu.getSanPhams().isEmpty()) {
            throw new RuntimeException("Không thể xóa thương hiệu này vì đang có sản phẩm sử dụng"); // Validation không cho xóa
        }
        thuongHieuRepository.deleteById(id);
    }
}