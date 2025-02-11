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
        thuongHieuRepository.save(thuongHieu);
    }

    public Optional<ThuongHieu> getThuongHieuById(UUID id) {
        return thuongHieuRepository.findById(id);
    }

    public void updateThuongHieu(ThuongHieu thuongHieu) {
        thuongHieuRepository.save(thuongHieu);
    }

    public void deleteThuongHieu(UUID id) {
        logger.info("Bắt đầu xóa ThuongHieu với ID: {}", id);

        Optional<ThuongHieu> thuongHieuOptional = thuongHieuRepository.findById(id);
        if (!thuongHieuOptional.isPresent()) {
            logger.warn("Không tìm thấy ThuongHieu với ID: {} để xóa", id);
            return; // Hoặc throw Exception, tùy yêu cầu
        }
        ThuongHieu thuongHieu = thuongHieuOptional.get();

        // Kiểm tra xem có SanPham nào sử dụng ThuongHieu này không
        List<SanPham> sanPhams = thuongHieu.getSanPhams(); // Lấy danh sách SanPham từ ThuongHieu

        if (sanPhams != null && !sanPhams.isEmpty()) {
            logger.warn("Không thể xóa ThuongHieu ID: {} vì có {} SanPham đang sử dụng", id, sanPhams.size());
            throw new DataIntegrityViolationException("Không thể xóa thương hiệu vì có sản phẩm đang sử dụng thương hiệu này.");
        }

        try {
            thuongHieuRepository.deleteById(id);
            logger.info("Xóa ThuongHieu thành công với ID: {}", id);
        } catch (DataIntegrityViolationException e) {
            logger.warn("DataIntegrityViolationException (từ DB) khi xóa ThuongHieu ID: {}", id, e);
            throw new DataIntegrityViolationException("Không thể xóa thương hiệu vì ràng buộc từ database.", e); // Ném lại exception từ DB
        } catch (Exception e) {
            logger.error("Lỗi không mong muốn khi xóa ThuongHieu ID: {}", id, e);
            throw e;
        }
    }
}