package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Category;

import java.util.List;

public interface CategoryService {
    Category findById(long id);

    List<Category> findAll();

    List<Category> activeCategory();

    void deleteCategory(long id);

}
