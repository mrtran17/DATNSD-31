//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.util.UUID;
//
//
//@Entity
//@Table(name = "dia_chi")
//@Data
//public class DiaChi {
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
//    @Column(name = "dia_chi_id", columnDefinition = "uniqueidentifier")
//    private UUID diaChiId;
//
//    @Column(name = "tai_khoan_id")
//    private String taiKhoanId;
//
//    @Column(name = "thanh_pho")
//    private String thanhPho;
//
//    @Column(name = "quan_huyen")
//    private String quanHuyen;
//
//    @Column(name = "phuong_xa")
//    private String phuongXa;
//
//    @Column(name = "mac_dinh")
//    private boolean macDinh;
//}
