package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Kieu_Dang")
@Data
public class KieuDang {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "kieu_dang_id", columnDefinition = "uniqueidentifier")
    private UUID kieuDangId;

    @Column(name = "ten_kieu_dang", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)") // Thêm columnDefinition = "NVARCHAR(255)"
    private String tenKieuDang;

    @Column(name = "trang_thai", length = 50, columnDefinition = "NVARCHAR(50)") // Thêm columnDefinition = "NVARCHAR(50)"
    private String trangThai;

    @OneToMany(mappedBy = "kieuDang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SanPham> sanPhams;
}