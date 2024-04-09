package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.EmailConfig;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.OTPConfig;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.AuthService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.auth.AuthenticationRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestParam String email) {
        return ResponseEntity.ok(authService.register(email));
    }

    @PostMapping("register/confirm")
    public ResponseEntity<?> registerConfirm(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.registerConfirm(userDTO));
    }

    @PostMapping("authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.authentication(authenticationRequest));
    }
}
