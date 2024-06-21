package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.ReviewRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Review;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @GetMapping
    public ResponseEntity<Page<Review>> getAllReview(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "{}") String filter,
                                                     @RequestParam(defaultValue = "25") int perPage,
                                                     @RequestParam(defaultValue = "createdAt") String sort,
                                                     @RequestParam(defaultValue = "DESC") String order) {
        Page<Review> reviews = reviewService.getAllReview(filter, page, perPage, sort, order);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable long id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    //admin
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateType(@PathVariable long id,@RequestBody Review review) {
        Review review1 = reviewService.updateType(id, review);
        return ResponseEntity.ok(review1);
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest) {
        Review review = reviewService.createReview(reviewRequest.getUserId(), reviewRequest.getProductId(), reviewRequest.getContent(), reviewRequest.getStars(), reviewRequest.getOrderDetailId());
        return ResponseEntity.ok(review);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewByProductId(@PathVariable long productId) {
        List<Review> reviews = reviewService.getReviewByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    //admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReviewById(@PathVariable long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Deleted");
    }
}
