package com.example.demo.service;

import com.example.demo.model.SanPham;
import com.example.demo.model.ThuongHieu;
import com.example.demo.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    private static final Logger logger = LoggerFactory.getLogger(ThuongHieuService.class);

    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepository.findAll();
    }

    public void saveThuongHieu(ThuongHieu thuongHieu) {
        // Validation trùng tên (trong Service)
        if (isTenThuongHieuDuplicate(thuongHieu.getTenThuongHieu(), thuongHieu.getThuongHieuId())) {
            throw new DataIntegrityViolationException("Tên thương hiệu đã tồn tại. Vui lòng chọn tên khác.");
        }
        thuongHieuRepository.save(thuongHieu);
    }

    public Optional<ThuongHieu> getThuongHieuById(UUID id) {
        return thuongHieuRepository.findById(id);
    }

    public void updateThuongHieu(ThuongHieu thuongHieu) {
        // Validation trùng tên (trong Service)
        if (isTenThuongHieuDuplicate(thuongHieu.getTenThuongHieu(), thuongHieu.getThuongHieuId())) {
            throw new DataIntegrityViolationException("Tên thương hiệu đã tồn tại. Vui lòng chọn tên khác.");
        }
        thuongHieuRepository.save(thuongHieu);
    }

    public void deleteThuongHieu(UUID id) {
        logger.info("Bắt đầu xóa ThuongHieu với ID: {}", id);

        Optional<ThuongHieu> thuongHieuOptional = thuongHieuRepository.findById(id);
        if (!thuongHieuOptional.isPresent()) {
            logger.warn("Không tìm thấy ThuongHieu với ID: {} để xóa", id);
            return;
        }
        ThuongHieu thuongHieu = thuongHieuOptional.get();

        List<SanPham> sanPhams = thuongHieu.getSanPhams();

        if (sanPhams != null && !sanPhams.isEmpty()) {
            logger.warn("Không thể xóa ThuongHieu ID: {} vì có {} SanPham đang sử dụng", id, sanPhams.size());
            throw new DataIntegrityViolationException("Không thể xóa thương hiệu vì có sản phẩm đang sử dụng thương hiệu này.");
        }

        try {
            thuongHieuRepository.deleteById(id);
            logger.info("Xóa ThuongHieu thành công với ID: {}", id);
        } catch (DataIntegrityViolationException e) {
            logger.warn("DataIntegrityViolationException (từ DB) khi xóa ThuongHieu ID: {}", id, e);
            throw new DataIntegrityViolationException("Không thể xóa thương hiệu vì ràng buộc từ database.", e);
        } catch (Exception e) {
            logger.error("Lỗi không mong muốn khi xóa ThuongHieu ID: {}", id, e);
            throw e;
        }
    }

    // Phương thức kiểm tra trùng tên thương hiệu
    private boolean isTenThuongHieuDuplicate(String tenThuongHieu, UUID thuongHieuId) {
        Optional<ThuongHieu> existingThuongHieu = thuongHieuRepository.findByTenThuongHieu(tenThuongHieu);
        if (existingThuongHieu.isPresent()) {
            if (thuongHieuId == null) { // Trường hợp thêm mới
                return true; // Trùng tên
            } else if (!existingThuongHieu.get().getThuongHieuId().equals(thuongHieuId)) { // Trường hợp cập nhật, kiểm tra nếu ID khác ID hiện tại
                return true; // Trùng tên với thương hiệu khác
            }
        }
        return false; // Không trùng tên
    }
}