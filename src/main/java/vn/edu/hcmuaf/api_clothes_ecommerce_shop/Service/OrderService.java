package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.OrderDto;

public interface OrderService {

    ResponseEntity<?> loadOrderDataById(long id);
    void updateDeliveryStatusPending(long orderId);
    ResponseEntity<?> orderWithPaymentMethodCOD(OrderDto orderDto);
}
