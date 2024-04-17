package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Page<Promotion> findAll(Specification<Promotion> specification, Pageable pageable);
}
