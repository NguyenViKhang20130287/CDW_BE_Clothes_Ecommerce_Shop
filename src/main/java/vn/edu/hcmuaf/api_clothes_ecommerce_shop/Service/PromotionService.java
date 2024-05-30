package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.PromotionDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Promotion;

import java.util.List;

public interface PromotionService {
    Page<PromotionDto> getAllPromotion(String filter, int page, int perPage, String sortBy, String order);
    Promotion getPromotionById(Long id);
    Promotion createPromotion(Promotion promotion);
    Promotion updatePromotion(Promotion promotion);
    List<Promotion> getPromotionsByIds(List<Long> ids);
}
