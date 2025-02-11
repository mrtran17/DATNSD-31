package com.example.demo.service;

import com.example.demo.model.KieuDang;
import com.example.demo.repository.KieuDangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KieuDangService {

    @Autowired
    private KieuDangRepository kieuDangRepository;

    public List<KieuDang> getAllKieuDang() {
        return kieuDangRepository.findAll();
    }
}