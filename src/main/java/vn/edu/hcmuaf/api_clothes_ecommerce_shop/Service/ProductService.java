package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    List<Product> getProductsStatusTrue();

    Product getProductById(Long id);

    Page<Product> getAllProducts(String filter, int page, int perPage, String sortBy, String order);

    void deleteProduct(Long id);

    Product createProduct(Product product);

}
