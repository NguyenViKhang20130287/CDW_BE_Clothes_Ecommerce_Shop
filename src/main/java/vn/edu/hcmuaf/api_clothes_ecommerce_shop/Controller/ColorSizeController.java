package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.ColorSize;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ColorSizeService;

@RestController
@RequestMapping("/api/v1/color-size")
public class ColorSizeController {
    ColorSizeService colorSizeService;
    @Autowired
    public ColorSizeController(ColorSizeService colorSizeService) {
        this.colorSizeService = colorSizeService;
    }
    @GetMapping
    public ResponseEntity<Page<ColorSize>> getAllColorSize(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "{}") String filter,
                                                           @RequestParam(defaultValue = "25") int perPage,
                                                           @RequestParam(defaultValue = "name") String sort,
                                                           @RequestParam(defaultValue = "DESC") String order) {
        Page<ColorSize> colorSizes = colorSizeService.getAllColorSize(filter, page, perPage, sort, order);
        return ResponseEntity.ok(colorSizes);
    }
}
