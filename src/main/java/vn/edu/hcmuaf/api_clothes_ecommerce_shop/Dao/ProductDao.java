package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao;

import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;

import java.util.List;

public interface ProductDao {
    Product findById(long id);
    List<Product> findAll();
    List<Product> activeProduct();
    List<Product> categoryProduct(long id);

}
