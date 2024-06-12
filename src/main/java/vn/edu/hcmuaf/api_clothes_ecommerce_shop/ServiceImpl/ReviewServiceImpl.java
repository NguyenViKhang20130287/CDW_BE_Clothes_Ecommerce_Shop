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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.JwtService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.OrderDetails;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Review;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.OrderDetailRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.ProductRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.ReviewRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.UserRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ReviewService;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderDetailRepository orderDetailRepository;
    private JwtService jwtService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             UserRepository userRepository,
                             ProductRepository productRepository,
                             OrderDetailRepository orderDetailRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public Page<Review> getAllReview(String filter, int page, int perPage, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Review> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterJson.has("stars")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("stars"), "%" + filterJson.get("stars").asText() + "%"));
            }
            return predicate;
        };
        if(sortBy.equals("createdAt")) {
            return reviewRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "createdAt")));
        }
        if (sortBy.equals("stars")) {
            return reviewRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "name")));
        }
        return reviewRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, sortBy)));
    }

    @Override
    public Review updateType(long id, Review review) {
        Review review1 = reviewRepository.findById(id).orElse(null);
        review1.setTypeStatus(review.getTypeStatus());
        return reviewRepository.save(review1);
    }

    @Override
    public Review createReview(long userId, long productId, String content, int stars, long orderDetailId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = formatter.format(new java.util.Date());
        Review review = new Review();
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        OrderDetails orderDetails = orderDetailRepository.findById(orderDetailId).orElse(null);
        review.setContent(content);
        review.setStars(stars);
        review.setUser(user);
        review.setProduct(product);
        review.setOrderDetails(orderDetails);
        review.setTypeStatus(0);
        review.setCreatedAt(date);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(long productId) {
        return reviewRepository.findAllByProductIdAndTypeStatus(productId, 1);
    }
}
