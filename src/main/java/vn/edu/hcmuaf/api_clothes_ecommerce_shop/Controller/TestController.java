package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.JwtService;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/")
    public ResponseEntity<?> sayHello() {
        return ResponseEntity.ok("Hello ok");
    }

    @PostMapping("/decode")
    public Claims decodeToken(
            @RequestParam String token
    ) {
        return new JwtService().decode(token);
    }
}
