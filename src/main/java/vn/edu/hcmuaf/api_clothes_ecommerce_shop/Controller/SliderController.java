package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Slider;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.SliderService;

@RestController
@RequestMapping("/api/v1/slider")
public class SliderController {
    private SliderService sliderService;

    @Autowired
    public SliderController(SliderService sliderService) {
        this.sliderService = sliderService;
    }

    @GetMapping
    public ResponseEntity<Page<Slider>> getAllSliders(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "{}") String filter,
                                                      @RequestParam(defaultValue = "25") int perPage,
                                                      @RequestParam(defaultValue = "id") String sort,
                                                      @RequestParam(defaultValue = "DESC") String order) {
        Page<Slider> sliders = sliderService.getAllSliders(filter, page, perPage, sort, order);
        return ResponseEntity.ok(sliders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Slider> getSliderById(@PathVariable long id) {
        Slider slider = sliderService.getSliderById(id);
        return ResponseEntity.ok(slider);
    }

    //admin
    @PostMapping
    public ResponseEntity<Slider> createSlider(@RequestBody Slider slider) {
        Slider newSlider = sliderService.createSlider(slider);
        return ResponseEntity.ok(newSlider);
    }

    //admin
    @PutMapping("/{id}")
    public ResponseEntity<Slider> updateSlider(@PathVariable long id, @RequestBody Slider slider) {
        Slider updatedSlider = sliderService.updateSlider(id, slider);
        return ResponseEntity.ok(updatedSlider);
    }

    //admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlider(@PathVariable long id) {
        sliderService.deleteSlider(id);
        return ResponseEntity.ok("Deleted");
    }

}
