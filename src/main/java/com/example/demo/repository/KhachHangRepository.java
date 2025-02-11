package com.example.demo.repository;

import com.example.demo.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, UUID> {
    Optional<KhachHang> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsBySoDienThoai(String soDienThoai);
    Optional<KhachHang> findFirstByOrderByMaKhachHangDesc();

}
