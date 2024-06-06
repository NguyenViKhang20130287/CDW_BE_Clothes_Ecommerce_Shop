package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.OrderDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.PaymentVNPAYDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.ProductOrderDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private DiscountCodeRepository discountCodeRepository;
    private DeliveryStatusHistoryRepository deliveryStatusHistoryRepository;
    private ProductRepository productRepository;
    private ColorRepository colorRepository;
    private SizeRepository sizeRepository;
    private ColorSizeRepository colorSizeRepository;
    private DeliveryStatusRepository deliveryStatusRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderDetailRepository orderDetailRepository,
                            DiscountCodeRepository discountCodeRepository,
                            DeliveryStatusHistoryRepository deliveryStatusHistoryRepository,
                            ProductRepository productRepository,
                            ColorRepository colorRepository,
                            SizeRepository sizeRepository,
                            ColorSizeRepository colorSizeRepository,
                            DeliveryStatusRepository deliveryStatusRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.discountCodeRepository = discountCodeRepository;
        this.deliveryStatusHistoryRepository = deliveryStatusHistoryRepository;
        this.productRepository = productRepository;
        this.deliveryStatusRepository = deliveryStatusRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.colorSizeRepository = colorSizeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> loadOrderDataById(long id) {
        return new ResponseEntity<>(
                orderRepository.findById(id).orElse(null),
                HttpStatus.OK
        );
    }

    @Override
    public void updateDeliveryStatus(long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            List<DeliveryStatusHistory> histories = order.getDeliveryStatusHistories();
            if (histories == null) {
                DeliveryStatus deliveryStatus = deliveryStatusRepository.findByName(status).orElse(null);
                DeliveryStatusHistory statusHistory = new DeliveryStatusHistory();
                statusHistory.setOrder(order);
                statusHistory.setDeliveryStatus(deliveryStatus);
                statusHistory.setCreatedAt(String.valueOf(LocalDateTime.now()));
                deliveryStatusHistoryRepository.save(statusHistory);
                System.out.println("Updated status pending");
            }
        }
    }

    @Override
    public ResponseEntity<?> updateResponseEntityStatus(PaymentVNPAYDto paymentVNPAYDto) {
        Order order = orderRepository.findById(paymentVNPAYDto.getOrderId()).orElse(null);
        if (order == null) return ResponseEntity.badRequest().body("Order doesn't exist !");
        List<DeliveryStatusHistory> histories = order.getDeliveryStatusHistories();
        if (histories == null) {
            DeliveryStatus deliveryStatus = deliveryStatusRepository.findByName(paymentVNPAYDto.getStatus()).orElse(null);
            DeliveryStatusHistory statusHistory = new DeliveryStatusHistory();
            statusHistory.setOrder(order);
            statusHistory.setDeliveryStatus(deliveryStatus);
            statusHistory.setCreatedAt(String.valueOf(LocalDateTime.now()));
            deliveryStatusHistoryRepository.save(statusHistory);
            System.out.println("Updated status pending");
        }
        return ResponseEntity.ok("Updated");
    }

    @Override
    public ResponseEntity<?> orderWithPaymentMethodCOD(OrderDto orderDto) {
        Order order = new Order();
        System.out.println("user id: " + orderDto.getUserId());
        if (orderDto.getUserId() != 0) {
            User user = userRepository.findById(orderDto.getUserId()).orElse(null);
            order.setUser(user);
        }
        order.setFullName(orderDto.getFullName());
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setPayment_method(orderDto.getPaymentMethod());
        order.setPayment_status(false);
        order.setTotal_amount(orderDto.getTotalAmount());
        if (!orderDto.getDiscountCode().isEmpty()) {
            DiscountCode discountCode = discountCodeRepository.findByCode(orderDto.getDiscountCode()).orElse(null);
            order.setDiscountCode(discountCode);
        }

        order.setShipping_cost(orderDto.getShippingCost());
        order.setCreated_at(String.valueOf(LocalDateTime.now()));
        orderRepository.save(order);

        OrderDetails od;
        for (ProductOrderDto product : orderDto.getProducts()) {
            od = new OrderDetails();
            Product p = productRepository.findById(product.getId()).orElse(null);
            Color color = colorRepository.findByName(product.getColor());
            Size size = sizeRepository.findByName(product.getSize());
            od.setProduct(p);
            od.setSize(size);
            od.setColor(color);
            assert p != null;
            od.setProduct_name(p.getName());
            od.setQuantity(product.getQuantity());
            od.setPrice(product.getPrice());
            od.setOrder(order);
            orderDetailRepository.save(od);
        }

        updateDeliveryStatus(order.getId(), "Pending");

        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<?> orderWithPaymentMethodVNPAY(OrderDto orderDto) {
        Order order = new Order();
        order.setFullName(orderDto.getFullName());
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setPayment_method(orderDto.getPaymentMethod());
        order.setPayment_status(false);
        order.setTotal_amount(orderDto.getTotalAmount());
        if (!orderDto.getDiscountCode().isEmpty()) {
            DiscountCode discountCode = discountCodeRepository.findByCode(orderDto.getDiscountCode()).orElse(null);
            order.setDiscountCode(discountCode);
        }

        order.setShipping_cost(orderDto.getShippingCost());
        order.setCreated_at(String.valueOf(LocalDateTime.now()));
        orderRepository.save(order);

        OrderDetails od;
        for (ProductOrderDto product : orderDto.getProducts()) {
            od = new OrderDetails();
            Product p = productRepository.findById(product.getId()).orElse(null);
            Color color = colorRepository.findByName(product.getColor());
            Size size = sizeRepository.findByName(product.getSize());
            od.setProduct(p);
            od.setSize(size);
            od.setColor(color);
            assert p != null;
            od.setProduct_name(p.getName());
            od.setQuantity(product.getQuantity());
            od.setPrice(product.getPrice());
            od.setOrder(order);
            orderDetailRepository.save(od);
        }

        updateDeliveryStatus(order.getId(), "Pending");

        return ResponseEntity.ok("Order VNPAY success");
    }

    public static void main(String[] args) {
    }
}
