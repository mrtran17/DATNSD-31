package com.example.demo.repository;

import com.example.demo.model.KieuDang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KieuDangRepository extends JpaRepository<KieuDang, UUID> {
}
