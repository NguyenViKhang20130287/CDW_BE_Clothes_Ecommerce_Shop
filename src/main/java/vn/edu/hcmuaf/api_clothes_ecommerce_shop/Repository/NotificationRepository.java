package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findAll(Specification<Notification> specification, Pageable pageable);
}
