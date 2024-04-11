package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Size;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.SizeService;

@RestController
@RequestMapping("/api/v1/size")
public class SizeController {
    private SizeService sizeService;

    @Autowired
    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping
    public ResponseEntity<Page<Size>> getAllSize(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "{}") String filter,
                                                 @RequestParam(defaultValue = "25") int perPage,
                                                 @RequestParam(defaultValue = "name") String sort,
                                                 @RequestParam(defaultValue = "DESC") String order) {
        Page<Size> sizes = sizeService.getAllSize(filter, page, perPage, sort, order);
        return ResponseEntity.ok(sizes);
    }
}
