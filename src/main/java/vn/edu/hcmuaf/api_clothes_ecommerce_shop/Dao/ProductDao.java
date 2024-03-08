package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao;

import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;

public interface ProductDao {
    Product findById(long id);
}
