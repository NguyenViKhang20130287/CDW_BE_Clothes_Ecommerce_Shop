package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Order;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.PermissionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {
    private PermissionService service;

    @Autowired
    public PermissionController(PermissionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
