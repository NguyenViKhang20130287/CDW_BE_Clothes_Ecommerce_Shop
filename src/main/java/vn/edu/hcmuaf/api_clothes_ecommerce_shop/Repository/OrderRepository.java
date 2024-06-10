package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Order;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(long id);
    Page<Order> findAll(Specification<Order> specification, Pageable pageable);
}
