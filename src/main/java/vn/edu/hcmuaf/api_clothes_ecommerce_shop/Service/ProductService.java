package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getProductsStatusTrue();
    Product getProductById(Long id);
    Page<Product> getAllProducts(String filter, int page, int perPage, String sortBy, String order);
    Page<Product> getProductsByCategory(Long categoryId,String filter, int page, int perPage, String sortBy, String order);
    void deleteProduct(Long id);
    Page<Product> sortProduct(int pageNum, String sortBy, String orderBy);

    Product createProduct(Product product);

    Product updateProduct(long productId, Product productUpdate);

    List<Product> getRelatedProducts(long productId);
    List<Product> searchProductsByName(String name);

    List<Product> getProductsByIds(String ids);
    ResponseEntity<?> find7ProductNewestByCateId();
}
