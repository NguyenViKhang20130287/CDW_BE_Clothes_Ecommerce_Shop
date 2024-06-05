package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.DiscountCodeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discount-code")
public class DiscountCodeController {

    private DiscountCodeService service;

    @Autowired
    public DiscountCodeController(DiscountCodeService service) {
        this.service = service;
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkCode(
            @RequestParam String code
    ) {
        return service.checkDiscountCode(code);
    }

}
