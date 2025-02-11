package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "San_Pham")
@Data
public class SanPham {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "san_pham_id", columnDefinition = "uniqueidentifier")
    private UUID sanPhamId;

    @Column(name = "ma_san_pham", nullable = false, unique = true, length = 20)
    private String maSanPham;

    @Column(name = "ten_san_pham", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)") // ĐÃ THÊM columnDefinition = "NVARCHAR(255)"
    private String tenSanPham;

    @ManyToOne
    @JoinColumn(name = "danh_muc_id", referencedColumnName = "danh_muc_id")
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "thuong_hieu_id", referencedColumnName = "thuong_hieu_id")
    private ThuongHieu thuongHieu;

    @ManyToOne
    @JoinColumn(name = "chat_lieu_id", referencedColumnName = "chat_lieu_id")
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "kieu_dang_id", referencedColumnName = "kieu_dang_id")
    private KieuDang kieuDang;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HinhAnh> hinhAnhs;

    @Column(name = "mo_ta", nullable = false, length = 255, columnDefinition = "NVARCHAR(255)")
    private String moTa;

    @Column(name = "ngay_them", updatable = false)
    private LocalDateTime ngayThem;

    @Column(name = "ngay_cap_nhap")
    private LocalDateTime ngayCapNhap;

    @Column(name = "trang_thai", columnDefinition = "NVARCHAR(50)") // ĐÃ THÊM columnDefinition = "NVARCHAR(50)"
    private String trangThai;

    @PrePersist
    protected void onCreate() {
        ngayThem = LocalDateTime.now();
        ngayCapNhap = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        ngayCapNhap = LocalDateTime.now();
    }
}