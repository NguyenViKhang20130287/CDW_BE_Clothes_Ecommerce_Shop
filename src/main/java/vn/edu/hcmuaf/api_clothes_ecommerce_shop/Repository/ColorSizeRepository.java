package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.ColorSize;

public interface ColorSizeRepository extends JpaRepository<ColorSize, Long> {
    Page<ColorSize> findAll(Specification<ColorSize> specification, Pageable pageable);
    ColorSize findByColorIdAndSizeIdAndProductId(Long colorId, Long sizeId, Long productId);
}
