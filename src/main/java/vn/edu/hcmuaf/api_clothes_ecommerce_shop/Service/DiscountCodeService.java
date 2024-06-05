package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.DiscountCodeRepository;

public interface DiscountCodeService {
    ResponseEntity<?> checkDiscountCode(String code);
}
