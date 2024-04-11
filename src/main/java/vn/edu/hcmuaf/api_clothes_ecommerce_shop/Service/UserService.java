package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<UserInformation> findAll(int page, int size, String sort, String order, String filter);
    List<UserInformation> findAll();
    User findByUsername(String username);
    UserInformation findByEmail(String email);
}
