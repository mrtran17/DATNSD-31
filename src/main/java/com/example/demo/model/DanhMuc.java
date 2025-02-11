package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Danh_Muc")
@Data
public class DanhMuc {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "danh_muc_id", columnDefinition = "uniqueidentifier")
    private UUID danhMucId;

    @Column(name = "ten_danh_muc", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)") // Thêm columnDefinition = "NVARCHAR(255)"
    private String tenDanhMuc;

    @Column(name = "trang_thai", length = 50, columnDefinition = "NVARCHAR(50)") // Thêm columnDefinition = "NVARCHAR(50)"
    private String trangThai;

    @OneToMany(mappedBy = "danhMuc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SanPham> sanPhams;
}