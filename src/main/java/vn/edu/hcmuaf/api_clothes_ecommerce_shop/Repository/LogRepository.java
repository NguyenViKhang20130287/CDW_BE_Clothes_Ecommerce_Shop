package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Log;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Order;

public interface LogRepository extends JpaRepository<Log, Long> {
    Page<Log> findAll(Specification<Log> specification, Pageable pageable);
}
