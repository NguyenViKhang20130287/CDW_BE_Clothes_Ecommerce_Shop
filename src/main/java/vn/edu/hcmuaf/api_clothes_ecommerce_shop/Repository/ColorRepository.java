package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {
    Color findByName(String name);
    Page<Color> findAll(Specification<Color> specification, Pageable pageable);
}
