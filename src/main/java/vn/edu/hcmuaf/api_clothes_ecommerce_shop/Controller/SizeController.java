package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/{id}")
    public ResponseEntity<Size> getSizeById(@PathVariable long id) {
        Size size = sizeService.getSizeById(id);
        return ResponseEntity.ok(size);
    }
    //admin
    @PostMapping
    public ResponseEntity<Size> addSize(@RequestBody Size size){
        Size newSize = sizeService.addSize(size);
        return ResponseEntity.ok(newSize);
    }

    //admin
    @PutMapping("/{id}")
    public ResponseEntity<Size> updateSize(@PathVariable long id, @RequestBody Size size){
        Size updatedSize = sizeService.updateSize(id, size);
        return ResponseEntity.ok(updatedSize);
    }

    //admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSize(@PathVariable long id){
        sizeService.deleteSize(id);
        return ResponseEntity.ok("Deleted");
    }
}
