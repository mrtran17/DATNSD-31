package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Thuong_Hieu")
@Data
public class ThuongHieu {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "thuong_hieu_id", columnDefinition = "uniqueidentifier")
    private UUID thuongHieuId;

    @Column(name = "ten_thuong_hieu", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)") // Thêm columnDefinition = "NVARCHAR(255)"
    private String tenThuongHieu;

    @Column(name = "trang_thai", length = 50, columnDefinition = "NVARCHAR(50)") // Thêm columnDefinition = "NVARCHAR(50)"
    private String trangThai;

    @OneToMany(mappedBy = "thuongHieu")
    private List<SanPham> sanPhams;
}