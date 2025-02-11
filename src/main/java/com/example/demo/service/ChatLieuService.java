package com.example.demo.service;

import com.example.demo.model.ChatLieu;
import com.example.demo.repository.ChatLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLieuService {

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    public List<ChatLieu> getAllChatLieu() {
        return chatLieuRepository.findAll();
    }
}