package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Review;

import java.util.List;

public interface ReviewService {
    Page<Review> getAllReview(String filter, int page, int perPage, String sortBy, String order);
    Review getReviewById(long id);
    Review updateType(long id, Review review);
    Review createReview(long userId, long productId, String content, int stars, long orderDetailId);
    List<Review> getReviewByProductId(long productId);
    void deleteReview(long id);
}
