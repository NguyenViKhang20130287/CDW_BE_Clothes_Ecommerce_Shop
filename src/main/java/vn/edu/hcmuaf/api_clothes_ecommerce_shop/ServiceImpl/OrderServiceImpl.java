package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.JwtService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.DeliveryStatusDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.OrderDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.PaymentVNPAYDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.ProductOrderDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Order;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.LogService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.OrderService;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private JwtService jwtService;
    private LogService logService;

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
                            UserRepository userRepository,
                            JwtService jwtService,
                            LogService logService
    ) {
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
        this.jwtService = jwtService;
        this.logService = logService;
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
                statusHistory.setCreatedAt(LocalDateTime.now());
                deliveryStatusHistoryRepository.save(statusHistory);
                System.out.println("Updated status pending");
            }
        }
    }

    @Override
    public ResponseEntity<?> updateResponseEntityStatus(PaymentVNPAYDto paymentVNPAYDto) {
        Order order = orderRepository.findById(paymentVNPAYDto.getOrderId()).orElse(null);
        if (order == null) return ResponseEntity.badRequest().body("Order doesn't exist !");
        DeliveryStatus status = deliveryStatusRepository.findByName(paymentVNPAYDto.getStatus()).orElse(null);
        DeliveryStatusHistory history = new DeliveryStatusHistory();
        history.setOrder(order);
        history.setDeliveryStatus(status);
        history.setCreatedAt(LocalDateTime.now());
        deliveryStatusHistoryRepository.save(history);
        order.setDeliveryStatus(status);
        if (paymentVNPAYDto.getStatus().equalsIgnoreCase("paid")){
            order.setPaymentStatus(true);
        }
        orderRepository.save(order);
        System.out.println("Updated " + paymentVNPAYDto.getStatus());
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<?> orderWithPaymentMethodCOD(OrderDto orderDto) {
        Order order = new Order();
        DeliveryStatus status = deliveryStatusRepository.findByName("Pending").orElse(null);
        if (orderDto.getUserId() != 0) {
            User user = userRepository.findById(orderDto.getUserId()).orElse(null);
            order.setUser(user);
        }
        order.setFullName(orderDto.getFullName());
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setPaymentStatus(orderDto.isPaymentStatus());
        order.setDeliveryStatus(status);
        order.setTotalAmount(orderDto.getTotalAmount());
        if (!orderDto.getDiscountCode().isEmpty()) {
            DiscountCode discountCode = discountCodeRepository.findByCode(orderDto.getDiscountCode()).orElse(null);
            order.setDiscountCode(discountCode);
        }

        order.setShippingCost(orderDto.getShippingCost());
        order.setCreatedAt(String.valueOf(LocalDateTime.now()));
        order.setDeleted(false);
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
            ColorSize colorSize = colorSizeRepository.findById(product.getColorSizeId()).orElse(null);
            if (colorSize == null) return ResponseEntity.badRequest().body("Color size not found");
            colorSize.setQuantity(colorSize.getQuantity() - product.getQuantity());
            colorSizeRepository.save(colorSize);
        }
        updateDeliveryStatus(order.getId(), "Pending");
        System.out.println("Order created: " + order.getId());
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<?> orderByNow(OrderDto orderDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> orderWithPaymentMethodVNPAY(OrderDto orderDto) {
        Order order = new Order();
        order.setFullName(orderDto.getFullName());
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setPaymentStatus(false);
        order.setTotalAmount(orderDto.getTotalAmount());
        if (!orderDto.getDiscountCode().isEmpty()) {
            DiscountCode discountCode = discountCodeRepository.findByCode(orderDto.getDiscountCode()).orElse(null);
            order.setDiscountCode(discountCode);
        }

        order.setShippingCost(orderDto.getShippingCost());
        order.setCreatedAt(String.valueOf(LocalDateTime.now()));
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

    @Override
    public Page<Order> findAll(int page, int size, String sort, String order, String filter) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sortBy;
        if (sort.equalsIgnoreCase("fullName")) {
            sortBy = Sort.by(direction, "fullName");
        } else if (sort.equals("totalAmount")) {
            sortBy = Sort.by(direction, "totalAmount");
        } else if (sort.equals("createdAt")) {
            sortBy = Sort.by(direction, "createdAt");
        } else {
            sortBy = Sort.by(direction, sort);
        }
        Pageable pageable = PageRequest.of(page, size, sortBy);
        JsonNode jsonFilter;
        try {
            jsonFilter = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Order> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (jsonFilter.has("paymentStatus")) {
                boolean paymentStatus = jsonFilter.get("paymentStatus").asBoolean();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
            }

            if (jsonFilter.has("isDeleted")) {
                boolean paymentStatus = jsonFilter.get("isDeleted").asBoolean();
                System.out.println("Deleted: " + paymentStatus);
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isDeleted"), paymentStatus));
            }

            if (jsonFilter.has("deliveryStatus")) {
                String deliveryName = jsonFilter.get("deliveryStatus").asText();
//                System.out.println("Delivery name: " + deliveryName);

                Join<Order, DeliveryStatus> join = root.join("deliveryStatus");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(join.get("name"), deliveryName));
            }

            if (jsonFilter.has("paymentMethod")) {
                String paymentMethod = jsonFilter.get("paymentMethod").asText();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod));
            }
            if (jsonFilter.has("date_gte")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                try {
                    java.util.Date date = dateFormat.parse(jsonFilter.get("date_gte").asText());
                    Timestamp dateGte = new Timestamp(date.getTime());
                    String dateGteString = dateGte.toString();
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), dateGteString));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            if (jsonFilter.has("q")) {
                String searchStr = jsonFilter.get("q").asText();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + searchStr.toLowerCase() + "%"));
            }

            return predicate;
        };

        return orderRepository.findAll(specification, pageable);
    }

    @Override
    public ResponseEntity<?> findById(long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return new ResponseEntity<>("Id order not found !", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getListProductByOrderId(long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order.getOrderDetails());
    }

    @Override
    public ResponseEntity<?> confirmOrder(long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return new ResponseEntity<>("Order not found !", HttpStatus.NOT_FOUND);
        DeliveryStatus confirmedStatus = deliveryStatusRepository.findByName("Confirmed").orElse(null);
        DeliveryStatus status = order.getDeliveryStatus();
        if (status.getName().equalsIgnoreCase("Pending") ||
                status.getName().equalsIgnoreCase("Paid")) {
            order.setDeliveryStatus(confirmedStatus);
            DeliveryStatusHistory history = new DeliveryStatusHistory();
            history.setOrder(order);
            history.setDeliveryStatus(confirmedStatus);
            history.setCreatedAt(LocalDateTime.now());
            List<DeliveryStatusHistory> histories = order.getDeliveryStatusHistories();
            histories.add(history);
            order.setDeliveryStatusHistories(histories);
            orderRepository.save(order);
            return ResponseEntity.ok(order);
        } else return new ResponseEntity<>("Failed action !", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> deleteOrder(long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return new ResponseEntity<>("Order not found !", HttpStatus.NOT_FOUND);
        order.setDeleted(true);
        orderRepository.save(order);
        System.out.println("Delete order: " + order.getId());
        return ResponseEntity.ok("Delete success");
    }

    @Override
    public ResponseEntity<?> getListOrderByToken(String token) {
        try {
            String username = jwtService.decode(token).getSubject();
            User user = userRepository.findByUsername(username).orElse(null);
            assert user != null;
            List<Order> orders = orderRepository.findAllByIsDeletedIsFalseAndUserIdOrderByCreatedAtDesc(user.getId());
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Token is expired", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getListDiscount() {
        return new ResponseEntity<>(discountCodeRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getListDeliveryStatus() {
        List<DeliveryStatus> deliveryStatuses = deliveryStatusRepository.findAll();
        return ResponseEntity.ok(deliveryStatuses);
    }

    @Override
    public ResponseEntity<?> edit(long id, DeliveryStatusDTO deliveryStatusDTO) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return ResponseEntity.badRequest().body("Order doesn't exist !");
        DeliveryStatus status = deliveryStatusRepository.findById(deliveryStatusDTO.getDeliveryId()).orElse(null);
        DeliveryStatusHistory history = new DeliveryStatusHistory();
        history.setOrder(order);
        history.setDeliveryStatus(status);
        history.setCreatedAt(LocalDateTime.now());
        deliveryStatusHistoryRepository.save(history);
        order.setDeliveryStatus(status);
        if (deliveryStatusDTO.getDeliveryId() == 1){
            order.setPaymentStatus(true);
        }
        orderRepository.save(order);
        assert status != null;
        System.out.println("Updated " + status.getName());
        return ResponseEntity.ok(order);
    }

    public static void main(String[] args) {
    }
}
