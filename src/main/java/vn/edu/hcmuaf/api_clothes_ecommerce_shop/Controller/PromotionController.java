package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Promotion;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.PromotionService;

@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionController {
    private PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

   @GetMapping
    public ResponseEntity<Page<Promotion>> getAllPromotion(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "{}") String filter,
                                                           @RequestParam(defaultValue = "25") int perPage,
                                                           @RequestParam(defaultValue = "name") String sort,
                                                           @RequestParam(defaultValue = "DESC") String order) {
        Page<Promotion> promotions = promotionService.getAllPromotion(filter, page, perPage, sort, order);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
        Promotion promotion = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) {
        Promotion newPromotion = promotionService.createPromotion(promotion);
        return ResponseEntity.ok(newPromotion);
    }
}
