package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findAll(Specification<Blog> specification, Pageable pageable);
}
