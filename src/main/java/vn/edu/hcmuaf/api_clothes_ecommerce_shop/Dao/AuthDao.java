package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

public interface AuthDao extends UserDetailsService {
    User findByEmail(String email);
    User findByUsername(String username);
    String getRole(User user);
    User register(UserDTO userDTO);
}
