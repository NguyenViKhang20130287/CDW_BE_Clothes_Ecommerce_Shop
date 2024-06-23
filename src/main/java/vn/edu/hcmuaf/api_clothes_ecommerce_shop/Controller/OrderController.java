package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.DeliveryStatusDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.OrderDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.PaymentVNPAYDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.DeliveryStatus;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Order;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.OrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<?> orderCOD(@RequestBody OrderDto orderDto) {
        return orderService.orderWithPaymentMethodCOD(orderDto);
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody PaymentVNPAYDto paymentVNPAYDto) {
        return orderService.updateResponseEntityStatus(paymentVNPAYDto);
    }

    @GetMapping("")
    public Page<Order> findAllAdmin(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int perPage,
            @RequestParam(defaultValue = "fullName") String sort,
            @RequestParam(defaultValue = "asc") String order,

            @RequestParam(defaultValue = "") String filter
    ) {
        return orderService.findAll(page, perPage, sort, order, filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return orderService.findById(id);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getListProductByOrderId(@PathVariable long id) {
        return orderService.getListProductByOrderId(id);
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirmOrder(@PathVariable long id) {
        return orderService.confirmOrder(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable long id, @RequestBody DeliveryStatusDTO deliveryStatusDTO){
        return orderService.edit(id, deliveryStatusDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable long id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("/find-by-user")
    public ResponseEntity<?> findByUser(@RequestParam String token) {
        return orderService.getListOrderByToken(token);
    }

    @GetMapping("/discounts")
    public ResponseEntity<?> getListDiscount(){
        return orderService.getListDiscount();
    }

    @GetMapping("/delivery-statuses")
    public ResponseEntity<?> getAllDeliveryStatus(){
        return orderService.getListDeliveryStatus();
    }

}
