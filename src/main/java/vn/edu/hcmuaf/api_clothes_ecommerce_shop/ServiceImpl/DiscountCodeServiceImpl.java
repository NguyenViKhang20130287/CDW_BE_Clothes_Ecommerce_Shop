package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.DiscountCode;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.DiscountCodeRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.DiscountCodeService;

@Service
@RequiredArgsConstructor
public class DiscountCodeServiceImpl implements DiscountCodeService {

    private DiscountCodeRepository codeRepository;

    @Autowired
    public DiscountCodeServiceImpl(DiscountCodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public ResponseEntity<?> checkDiscountCode(String code) {
        DiscountCode discount = codeRepository.findByCode(code).orElse(null);
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }
}
