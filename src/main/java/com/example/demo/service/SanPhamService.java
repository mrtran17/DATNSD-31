package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private DanhMucRepository danhMucRepository;
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;
    @Autowired
    private ChatLieuRepository chatLieuRepository;
    @Autowired
    private KieuDangRepository kieuDangRepository;
    @Autowired
    private HinhAnhRepository hinhAnhRepository;

    public void addNewSanPham(SanPham sanPham, MultipartFile[] imageFiles) throws Exception {

        if (sanPham.getMaSanPham() == null || sanPham.getMaSanPham().isEmpty()) {
            sanPham.setMaSanPham(generateMaSanPham());
        }
        // 1. Lấy các entity liên quan từ database dựa trên UUID được gửi từ form
        DanhMuc danhMuc = danhMucRepository.findById(sanPham.getDanhMuc().getDanhMucId())
                .orElseThrow(() -> new Exception("Danh mục không tồn tại"));
        ThuongHieu thuongHieu = thuongHieuRepository.findById(sanPham.getThuongHieu().getThuongHieuId())
                .orElseThrow(() -> new Exception("Thương hiệu không tồn tại"));
        ChatLieu chatLieu = chatLieuRepository.findById(sanPham.getChatLieu().getChatLieuId())
                .orElseThrow(() -> new Exception("Chất liệu không tồn tại"));
        KieuDang kieuDang = kieuDangRepository.findById(sanPham.getKieuDang().getKieuDangId())
                .orElseThrow(() -> new Exception("Kiểu dáng không tồn tại"));

        // 2. Set các entity liên quan vào SanPham
        sanPham.setDanhMuc(danhMuc);
        sanPham.setThuongHieu(thuongHieu);
        sanPham.setChatLieu(chatLieu);
        sanPham.setKieuDang(kieuDang);

        // 3. Lưu SanPham vào database
        SanPham savedSanPham = sanPhamRepository.save(sanPham);

        // 4. Xử lý và lưu hình ảnh (nếu có file được upload)
        if (imageFiles != null && imageFiles.length > 0) {
            List<HinhAnh> hinhAnhs = new ArrayList<>();
            for (MultipartFile imageFile : imageFiles) {
                if (!imageFile.isEmpty()) { // Kiểm tra file có rỗng không
                    try {
                        // Lưu file hình ảnh và lấy đường dẫn URL
                        String imageUrl = saveImageFile(imageFile, savedSanPham.getSanPhamId());

                        HinhAnh hinhAnh = new HinhAnh();
                        hinhAnh.setHinhAnhUrl(imageUrl);
                        hinhAnh.setSanPham(savedSanPham); // Liên kết HinhAnh với SanPham vừa tạo
                        hinhAnhs.add(hinhAnh);
                    } catch (IOException e) {
                        // Xử lý lỗi khi lưu file (ví dụ: log lỗi, thông báo cho admin)
                        System.err.println("Lỗi khi lưu file hình ảnh: " + imageFile.getOriginalFilename() + " - " + e.getMessage());
                        // Không throw exception ở đây, tiếp tục xử lý các file khác nếu có
                    }
                }
            }
            hinhAnhRepository.saveAll(hinhAnhs); // Lưu danh sách HinhAnh vào database
        }
    }

    // Method helper để lưu file hình ảnh (ví dụ, lưu vào thư mục "product-images")
    private String saveImageFile(MultipartFile imageFile, UUID sanPhamId) throws IOException {
        String uploadDir = "product-images/"; // Thư mục lưu ảnh (tạo thư mục này trong project)
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension; // Tên file duy nhất
        Path filePath = uploadPath.resolve(uniqueFilename);

        try (InputStream inputStream = imageFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Không thể lưu file hình ảnh: " + originalFilename, e);
        }

        return "/" + uploadDir + uniqueFilename; // Đường dẫn URL tương đối để truy cập ảnh từ web
    }

    private String generateMaSanPham() {
        // Lấy số lượng sản phẩm hiện tại
        long count = sanPhamRepository.count() + 1;

        // Định dạng số với 7 chữ số (VD: 0000017)
        String numberPart = String.format("%07d", count);

        // Sinh 2 ký tự chữ cái ngẫu nhiên (A-Z)
        String letters = generateRandomLetters(2);

        return "VN" + numberPart + letters;
    }

    // Hàm sinh ký tự ngẫu nhiên
    private String generateRandomLetters(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }


    public List<SanPham> getAllSanPham() { // <-- HÀM BẠN CẦN
        return sanPhamRepository.findAll();
    }
}