package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Notification;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

import java.util.List;

public interface NotificationService {
    Page<Notification> findAll(int page, int size, String sort, String order, String filter);
    void sendNotify(List<User> users, String content);
    ResponseEntity<?> seenNotification(long id);
}
