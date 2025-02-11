package com.example.demo.repository;

import com.example.demo.model.ThuongHieu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, UUID> {
}
