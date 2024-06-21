package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Color;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ColorService;

@RestController
@RequestMapping("/api/v1/color")
public class ColorController {
    private ColorService colorService;

    @Autowired
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping
    public ResponseEntity<Page<Color>> getAllColor(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "{}") String filter,
                                                   @RequestParam(defaultValue = "25") int perPage,
                                                   @RequestParam(defaultValue = "name") String sort,
                                                   @RequestParam(defaultValue = "DESC") String order) {
        Page<Color> colors = colorService.getAllColor(filter, page, perPage, sort, order);
        return ResponseEntity.ok(colors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getColorById(@PathVariable Long id) {
        Color color = colorService.getColorById(id);
        return ResponseEntity.ok(color);
    }

    //admin
    @PostMapping()
    public ResponseEntity<Color> addColor(@RequestBody Color color){
        Color newColor = colorService.addColor(color);
        return ResponseEntity.ok(newColor);
    }

    //admin
    @PutMapping("/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable Long id, @RequestBody Color color){
        Color updatedColor = colorService.updateColor(id, color);
        return ResponseEntity.ok(updatedColor);
    }

    //admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteColor(@PathVariable Long id){
        colorService.deleteColor(id);
        return ResponseEntity.ok("Deleted");
    }
}
