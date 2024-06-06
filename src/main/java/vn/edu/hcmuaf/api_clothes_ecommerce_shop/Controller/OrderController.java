package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.OrderDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.PaymentVNPAYDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.OrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<?> orderCOD(@RequestBody OrderDto orderDto){
        return orderService.orderWithPaymentMethodCOD(orderDto);
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody PaymentVNPAYDto paymentVNPAYDto){
        return orderService.updateResponseEntityStatus(paymentVNPAYDto);
    }
}
