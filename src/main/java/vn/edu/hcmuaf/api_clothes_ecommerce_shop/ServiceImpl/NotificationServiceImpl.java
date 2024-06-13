package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Notification;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.NotificationRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.NotificationService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Page<Notification> findAll(int page, int size, String sort, String order, String filter) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sortBy;
//        if (sort.equalsIgnoreCase("fullName")) {
//            sortBy = Sort.by(direction, "fullName");
//        } else {
            sortBy = Sort.by(direction, sort);
//        }
        Pageable pageable = PageRequest.of(page, size, sortBy);
        JsonNode jsonFilter;
        try {
            jsonFilter = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Notification> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

//            if (jsonFilter.has("paymentStatus")) {
//                boolean paymentStatus = jsonFilter.get("paymentStatus").asBoolean();
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
//            }
//
//            if (jsonFilter.has("q")) {
//                String searchStr = jsonFilter.get("q").asText();
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + searchStr.toLowerCase() + "%"));
//            }

            return predicate;
        };

        return notificationRepository.findAll(specification, pageable);
    }

    @Override
    public void sendNotify(List<User> users, String content) {
        for (User user : users){
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setContent(content);
            notification.setSeen(false);
            notification.setCreatedAt(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    @Override
    public ResponseEntity<?> seenNotification(long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        assert notification != null;
        notification.setSeen(true);
        notificationRepository.save(notification);
        return new ResponseEntity<>("seen", HttpStatus.OK);
    }
}
