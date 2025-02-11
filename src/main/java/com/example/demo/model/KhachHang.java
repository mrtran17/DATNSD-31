package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "khach_hang")
@Data
public class KhachHang {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "khach_hang_id", columnDefinition = "uniqueidentifier")
    private UUID khachHangId;

    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @Column(name = "ten", columnDefinition = "nvarchar(255)") // Set type to nvarchar
    private String ten;

    @Column(name = "email")
    private String email;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "trang_thai")
    private boolean trangThai; // Set type to boolean

}