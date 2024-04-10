package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Color;

public interface ColorRepository extends JpaRepository<Color, Long> {
    Color findByName(String name);
}
