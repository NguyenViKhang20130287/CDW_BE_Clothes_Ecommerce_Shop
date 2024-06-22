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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Blog;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.BlogRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.BlogService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

@Service
public class BlogServiceImpl implements BlogService {
    private BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

  @Override
        public Page<Blog> getAllBlog(String filter, int page, int perPage, String sortBy, String order) {
            Sort.Direction direction = Sort.Direction.ASC;
            if (order.equalsIgnoreCase("DESC"))
                direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Blog> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterJson.has("q")) {
                String searchStr = filterJson.get("q").asText();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + searchStr.toLowerCase() + "%"));
            }
            if (filterJson.has("status")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filterJson.get("status").asBoolean()));
            }
            if (filterJson.has("createdAt")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("createdAt"), filterJson.get("createdAt").asText()));
            }
            if (filterJson.has("isDeleted")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isDeleted"), filterJson.get("isDeleted").asBoolean()));
            }
            return predicate;
        };
        if (sortBy.equals("title")) {
            return blogRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "title")));
        }
        if (sortBy.equals("status")) {
            return blogRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "status")));
        }
        if (sortBy.equals("createdAt")) {
            return blogRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "createdAt")));
        }
        return blogRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, sortBy)));
    }

    @Override
    public Blog getBlogById(long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Blog createBlog(Blog newBlog) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newBlog.setCreatedAt(formatter.format(new java.util.Date()));
        newBlog.setUpdatedAt(formatter.format(new java.util.Date()));
        return blogRepository.save(newBlog);
    }

    @Override
    public Blog updateBlog(long id, Blog newBlog) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            return null;
        }
        blog.setTitle(newBlog.getTitle());
        blog.setDescription(newBlog.getDescription());
        blog.setContent(newBlog.getContent());
        blog.setStatus(newBlog.isStatus());
        blog.setThumbnail(newBlog.getThumbnail());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        blog.setUpdatedAt(formatter.format(new java.util.Date()));
        blog.setUpdatedBy(newBlog.getUpdatedBy());
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        blog.setDeleted(true);
        blogRepository.save(blog);
    }
}
