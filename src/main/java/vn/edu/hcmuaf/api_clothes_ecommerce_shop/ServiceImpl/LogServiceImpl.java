package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.DeliveryStatus;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Log;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Order;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.LogRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.UserRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.LogService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private LogRepository logRepository;
    private UserRepository userRepository;
    private JwtService service;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, UserRepository userRepository, JwtService service) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
        this.service = service;
    }


    @Override
    public void addLog(long userId, String action) {
        User user = userRepository.findById(userId).orElse(null);
        Log log = new Log();
        log.setTimeStamp(LocalDateTime.now());
        log.setUser(user);
        log.setAction(action);
        logRepository.save(log);
        System.out.println("Add log success");
    }

    @Override
    public ResponseEntity<?> addLogApi(String token, String action) {
        String username = service.decode(token).getSubject();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return new ResponseEntity<>("User not found !", HttpStatus.NOT_FOUND);
        Log log = new Log();
        log.setTimeStamp(LocalDateTime.now());
        log.setUser(user);
        log.setAction(action);
        logRepository.save(log);
        System.out.println("Add log success");
        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    @Override
    public Page<Log> findAll(int page, int size, String sort, String order, String filter) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sortBy;
        if (sort.equalsIgnoreCase("timeStamp")) {
            sortBy = Sort.by(direction, "timeStamp");
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
        Specification<Log> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

//            if (jsonFilter.has("paymentStatus")) {
//                boolean paymentStatus = jsonFilter.get("paymentStatus").asBoolean();
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
//            }
//
//            if (jsonFilter.has("deliveryStatus")) {
//                String deliveryName = jsonFilter.get("deliveryStatus").asText();
////                System.out.println("Delivery name: " + deliveryName);
//
//                Join<Order, DeliveryStatus> join = root.join("deliveryStatus");
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(join.get("name"), deliveryName));
//            }
//
//            if (jsonFilter.has("paymentMethod")) {
//                String paymentMethod = jsonFilter.get("paymentMethod").asText();
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod));
//            }
//
//            if (jsonFilter.has("q")) {
//                String searchStr = jsonFilter.get("q").asText();
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + searchStr.toLowerCase() + "%"));
//            }

            return predicate;
        };

        return logRepository.findAll(specification, pageable);
    }
}
