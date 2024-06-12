package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.DeliveryStatusHistory;

import java.util.List;
import java.util.Optional;

public interface DeliveryStatusHistoryRepository extends JpaRepository<DeliveryStatusHistory, Long> {
    DeliveryStatusHistory findFirstByOrder_IdOrderByCreatedAtDesc(long id);
}
