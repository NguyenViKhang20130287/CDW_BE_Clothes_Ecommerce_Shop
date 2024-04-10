package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.ProductRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ProductService;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsStatusTrue() {
        return productRepository.findByStatusTrue();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Product> getAllProducts(String filter, int page, int perPage, String sortBy, String order) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> sortProduct(int pageNum, String sortBy, String orderBy) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (orderBy.equalsIgnoreCase("desc")){
            direction = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(pageNum, 10, sort);
        return productRepository.findAll(pageable);
    }

}
