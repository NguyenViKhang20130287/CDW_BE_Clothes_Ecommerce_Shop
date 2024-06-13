package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.NotificationService;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @PostMapping("/seen/{id}")
    public ResponseEntity<?> seenNotification(@PathVariable long id){
        return notificationService.seenNotification(id);
    }
}
