package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.SliderService;

@RestController
@RequestMapping("/api/v1/slider/")
public class SliderController {
    private SliderService sliderService;

    @Autowired
    public SliderController(SliderService sliderService) {
        this.sliderService = sliderService;
    }

    @GetMapping("all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(sliderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("active")
    public ResponseEntity<?> activeSlider() {
        return new ResponseEntity<>(sliderService.activeSlider(), HttpStatus.OK);
    }
}
