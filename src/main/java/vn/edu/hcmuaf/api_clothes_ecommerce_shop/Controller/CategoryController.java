package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Category;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories( @RequestParam(defaultValue = "{}") String filter,
                                                            @RequestParam(defaultValue = "1") int pageNum,
                                                            @RequestParam(defaultValue = "5") int pageSize,
                                                            @RequestParam(defaultValue = "name") String sort,
                                                            @RequestParam(defaultValue = "asc") String order) {
        Page<Category> categories = categoryService.getAllCategories(filter, pageNum, pageSize, sort, order);
        return ResponseEntity.ok(categories);
    }


}
