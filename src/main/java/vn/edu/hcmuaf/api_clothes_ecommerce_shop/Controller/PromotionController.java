package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.PromotionDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Promotion;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionController {
    private PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

   @GetMapping
    public ResponseEntity<Page<PromotionDto>> getAllPromotion(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "{}") String filter,
                                                              @RequestParam(defaultValue = "25") int perPage,
                                                              @RequestParam(defaultValue = "name") String sort,
                                                              @RequestParam(defaultValue = "DESC") String order) {
        Page<PromotionDto> promotions = promotionService.getAllPromotion(filter, page, perPage, sort, order);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionDto> getPromotionById(@PathVariable Long id) {
        return ResponseEntity.ok(promotionService.getPromotionById(id));
    }

    //admin
    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) {
        Promotion newPromotion = promotionService.createPromotion(promotion);
        return ResponseEntity.ok(newPromotion);
    }

    //admin
    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable Long id, @RequestBody Promotion promotion) {
        return ResponseEntity.ok(promotionService.updatePromotion(id, promotion));
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Promotion>> getPromotionsByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(promotionService.getPromotionsByIds(ids));
    }

    //admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.ok("Deleted");
    }
}
