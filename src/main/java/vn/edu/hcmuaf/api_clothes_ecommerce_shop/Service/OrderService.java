package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.DeliveryStatusDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.OrderDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.PaymentVNPAYDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Order;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

public interface OrderService {

    ResponseEntity<?> loadOrderDataById(long id);
    void updateDeliveryStatus(long orderId, String status);
    ResponseEntity<?> updateResponseEntityStatus(PaymentVNPAYDto paymentVNPAYDto);
    ResponseEntity<?> orderWithPaymentMethodCOD(OrderDto orderDto);
    ResponseEntity<?> orderByNow(OrderDto orderDto);
    ResponseEntity<?> orderWithPaymentMethodVNPAY(OrderDto orderDto);
    Page<Order> findAll(int page, int size, String sort, String order, String filter);
    ResponseEntity<?> findById(long id);
    ResponseEntity<?> getListProductByOrderId(long id);
    ResponseEntity<?> confirmOrder(long id);
    ResponseEntity<?> deleteOrder(long id);
    ResponseEntity<?> getListOrderByToken(String token);
    ResponseEntity<?> getListDiscount();
    ResponseEntity<?> getListDeliveryStatus();
    ResponseEntity<?> edit(long id, DeliveryStatusDTO deliveryStatusDTO);
}
