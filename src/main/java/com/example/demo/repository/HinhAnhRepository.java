package com.example.demo.repository;

import com.example.demo.model.HinhAnh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HinhAnhRepository extends JpaRepository<HinhAnh, UUID> {
}
