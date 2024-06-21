package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Blog;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.BlogService;

@Controller
@RequestMapping("/api/v1/blog")
public class BlogController {
    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<Page<Blog>> getAllBlog(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "{}") String filter,
                                                 @RequestParam(defaultValue = "25") int perPage,
                                                 @RequestParam(defaultValue = "createdAt") String sort,
                                                 @RequestParam(defaultValue = "DESC") String order) {
        Page<Blog> blogs = blogService.getAllBlog(filter, page, perPage, sort, order);
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable long id) {
        Blog blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }

    //admin
    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog newBlog) {
        Blog blog = blogService.createBlog(newBlog);
        return ResponseEntity.ok(blog);
    }

    //admin
    @PutMapping({"/{id}"})
    public ResponseEntity<Blog> updateBlog(@PathVariable long id, @RequestBody Blog newBlog) {
        Blog blog = blogService.updateBlog(id, newBlog);
        return ResponseEntity.ok(blog);
    }

    //admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok("Deleted");
    }

}
