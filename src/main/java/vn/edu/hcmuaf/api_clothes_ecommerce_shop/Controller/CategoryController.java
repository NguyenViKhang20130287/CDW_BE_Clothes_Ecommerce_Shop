package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.CategoryService;

@RestController
@RequestMapping("/api/v1/category/")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }
    @GetMapping("find/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }
    @GetMapping("find/active")
    public ResponseEntity<?> activeCategory() {
        return new ResponseEntity<>(categoryService.activeCategory(), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }


}
