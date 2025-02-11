package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "Hinh_Anh")
@Data
public class HinhAnh {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "hinh_anh_id", columnDefinition = "uniqueidentifier")
    private UUID hinhAnhId;

    @Column(name = "hinh_anh_url", length = 255)
    private String hinhAnhUrl;

    @ManyToOne
    @JoinColumn(name = "san_pham_id", referencedColumnName = "san_pham_id")
    private SanPham sanPham;
}