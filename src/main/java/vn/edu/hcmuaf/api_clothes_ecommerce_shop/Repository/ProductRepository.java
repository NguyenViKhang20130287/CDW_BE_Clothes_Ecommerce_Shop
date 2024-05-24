package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByStatusTrue();
    Page<Product> findAll(Specification<Product> specification, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 AND p.id <> ?2")
    List<Product> findRelatedProducts(Long categoryId, Long productId, Pageable pageable);
}
