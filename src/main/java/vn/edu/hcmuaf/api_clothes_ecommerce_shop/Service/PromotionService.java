package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Promotion;

public interface PromotionService {
    Page<Promotion> getAllPromotion(String filter, int page, int perPage, String sortBy, String order);
}
