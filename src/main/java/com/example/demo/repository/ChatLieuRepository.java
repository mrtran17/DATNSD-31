package com.example.demo.repository;

import com.example.demo.model.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatLieuRepository extends JpaRepository<ChatLieu, UUID> {
}
