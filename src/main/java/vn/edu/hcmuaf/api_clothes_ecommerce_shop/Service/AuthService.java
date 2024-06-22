package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.auth.AuthenticationRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.auth.AuthenticationResponse;

import java.util.Optional;

public interface AuthService {
    UserInformation findByEmail(String email);
    ResponseEntity<?> register(String email);
    ResponseEntity<?> registerConfirm(UserDTO userDTO);
    ResponseEntity<?> login(AuthenticationRequest authenticationRequest);
    ResponseEntity<?> forgot(String email);
    ResponseEntity<?> reset(UserDTO userDTO);
    ResponseEntity<?> checkAuth(String token);
    ResponseEntity<?> checkRole(String token);
}
