package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.DiscountCodeDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.DiscountCode;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Notification;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.DiscountCodeRepository;

public interface DiscountCodeService {
    ResponseEntity<?> checkDiscountCode(String code);
    Page<DiscountCode> findAll(int page, int size, String sort, String order, String filter);
    ResponseEntity<?> findById(long id);
    ResponseEntity<?> create(DiscountCodeDTO discountCodeDTO);
    ResponseEntity<?> delete(long id);
    ResponseEntity<?> edit(long id, DiscountCodeDTO discountCodeDTO);
    ResponseEntity<?> discountIsUsed(long id);
}
