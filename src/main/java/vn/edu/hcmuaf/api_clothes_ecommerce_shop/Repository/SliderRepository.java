package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Slider;

public interface SliderRepository extends JpaRepository<Slider, Long> {
    Page<Slider> findAll(Specification<Slider> specification, Pageable pageable);
}
