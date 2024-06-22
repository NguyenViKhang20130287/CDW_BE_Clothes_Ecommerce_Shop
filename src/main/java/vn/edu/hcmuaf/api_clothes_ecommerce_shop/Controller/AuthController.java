package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
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

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.login(authenticationRequest));
    }

    @PostMapping("forgot-password")
    public ResponseEntity<?> forgot(@RequestParam String email){
        return ResponseEntity.ok(authService.forgot(email));
    }

    @PostMapping("forgot-password/reset")
    public ResponseEntity<?> reset(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(authService.reset(userDTO));
    }

    @PostMapping("/check-expired")
    public ResponseEntity<?> checkAuth(@RequestParam String token){
        return ResponseEntity.ok(authService.checkAuth(token));
    }

    @GetMapping("/check-role")
    public ResponseEntity<?> checkRole(@RequestParam String token){
        return authService.checkRole(token);
    }
}
