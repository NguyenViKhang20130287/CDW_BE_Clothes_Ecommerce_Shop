package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Category;

import java.util.List;

public interface CategoryService {
    Page<Category> getAllCategories(String filter, int start, int end, String sortBy, String order);
    List<Category> getCategoriesStatusTrue();
    Category getCategoryById(long id);
    Category createCategory(Category category);
    Category updateCategory(long id, Category category);
    void deleteCategory(long id);
}
