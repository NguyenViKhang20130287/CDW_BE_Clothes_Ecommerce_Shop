package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll(int pageNum, int pageSize, String sort, String order);
}
