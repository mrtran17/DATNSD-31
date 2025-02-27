package com.example.demo.repository;

import com.example.demo.model.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {
    boolean existsByTenThuongHieu(String tenThuongHieu); // Kiểm tra trùng tên

    boolean existsByTenThuongHieuAndThuongHieuIdNot(String tenThuongHieu, Integer thuongHieuId); // Kiểm tra trùng tên khi sửa

    Page<ThuongHieu> findByTenThuongHieuContainingIgnoreCase(String tenThuongHieu, Pageable pageable); // Tìm kiếm theo tên (phân trang)

    List<ThuongHieu> findByTenThuongHieuContainingIgnoreCase(String tenThuongHieu); // Tìm kiếm theo tên (không phân trang - cho dropdown lọc)
}