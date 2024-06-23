package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Category;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.CategoryRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.ProductRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.CategoryService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.UserService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Page<Category> getAllCategories(String filter, int start, int end, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Category> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterJson.has("q")) {
                String searchStr = filterJson.get("q").asText();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchStr.toLowerCase() + "%"));
            }
            if (filterJson.has("status")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filterJson.get("status").asBoolean()));
            }
            if (filterJson.has("isDeleted")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isDeleted"), filterJson.get("isDeleted").asBoolean()));
            }
            return predicate;
        };

        if (sortBy.equals("name")) {
            return categoryRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, "name")));
        }
        if (sortBy.equals("status")) {
            return categoryRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, "status")));
        }
        return categoryRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, sortBy)));
    }

    @Override
    public List<Category> getCategoriesStatusTrue() {
        return categoryRepository.getAllByStatusIsTrue();
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category createCategory(Category category) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        category.setDeleted(false);

        category.setCreatedAt(formatter.format(new Date()));
        category.setUpdatedAt(formatter.format(new Date()));
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(long id, Category category) {
        Category categoryUpdate = categoryRepository.findById(id).orElse(null);
        categoryUpdate.setName(category.getName());
        categoryUpdate.setStatus(category.isStatus());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        categoryUpdate.setUpdatedAt(formatter.format(new Date()));
        categoryUpdate.setUpdatedBy(category.getUpdatedBy());
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public void deleteCategory(long id) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Category category = categoryRepository.findById(id).orElse(null);
        assert category != null;
        for(Product product : category.getProducts()){
            product.setDeleted(true);
            product.setUpdatedAt(formatter.format(new Date()));
            productRepository.save(product);
        }
        category.setDeleted(true);
        category.setUpdatedAt(formatter.format(new Date()));
        categoryRepository.save(category);
    }

}
