package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.DiscountCodeDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.DiscountCode;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Order;
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

    @GetMapping("")
    public Page<DiscountCode> findAllAdmin(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int perPage,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String filter
    ) {
        return service.findAll(page, perPage, sort, order, filter);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return service.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody DiscountCodeDTO discountCodeDTO){
        return service.create(discountCodeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        return service.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @PathVariable long id,
            @RequestBody DiscountCodeDTO discountCodeDTO){
        return service.edit(id, discountCodeDTO);
    }

    @PostMapping("/used/{id}")
    public ResponseEntity<?> used(@PathVariable long id){
        return service.discountIsUsed(id);
    }

}
