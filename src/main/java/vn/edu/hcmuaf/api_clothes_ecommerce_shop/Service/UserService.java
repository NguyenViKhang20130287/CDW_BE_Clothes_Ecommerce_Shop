package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> findAllUser(int page, int size);
    User findByUsername(String username);
    UserInformation findByEmail(String email);
}
