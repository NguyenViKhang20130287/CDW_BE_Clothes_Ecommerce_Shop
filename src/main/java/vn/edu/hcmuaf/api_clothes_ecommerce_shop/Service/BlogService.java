package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Blog;

public interface BlogService {
    Page<Blog> getAllBlog(String filter, int page, int perPage, String sortBy, String order);
    Blog getBlogById(long id);
    Blog createBlog(Blog newBlog);
    Blog updateBlog(long id, Blog newBlog);
    void deleteBlog(long id);
}
