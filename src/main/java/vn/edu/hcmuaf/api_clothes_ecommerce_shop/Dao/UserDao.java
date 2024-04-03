package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao;

import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAll(int pageNum, int pageSize, String sort, String order);
}
