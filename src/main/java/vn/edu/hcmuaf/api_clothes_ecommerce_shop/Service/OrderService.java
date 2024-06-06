package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.OrderDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.PaymentVNPAYDto;

public interface OrderService {

    ResponseEntity<?> loadOrderDataById(long id);
    void updateDeliveryStatus(long orderId, String status);
    ResponseEntity<?> updateResponseEntityStatus(PaymentVNPAYDto paymentVNPAYDto);
    ResponseEntity<?> orderWithPaymentMethodCOD(OrderDto orderDto);
    ResponseEntity<?> orderWithPaymentMethodVNPAY(OrderDto orderDto);
}
