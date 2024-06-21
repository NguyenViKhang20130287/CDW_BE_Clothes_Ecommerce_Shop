package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/findAll/sort={sortBy}/order={orderBy}/pageNum={pageNum}")
    public Page<Product> findAll(
            @PathVariable String sortBy,
            @PathVariable String orderBy,
            @PathVariable int pageNum
    ) {
        return productService.sortProduct(pageNum, sortBy, orderBy);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "{}") String filter,
                                                        @RequestParam(defaultValue = "16") int perPage,
                                                        @RequestParam(defaultValue = "name") String sort,
                                                        @RequestParam(defaultValue = "DESC") String order) {
        Page<Product> products = productService.getAllProducts(filter, page, perPage, sort, order);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<Product>> getProductsByCategory(@PathVariable Long categoryId,
                                                               @RequestParam(defaultValue = "{}") String filter,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "16") int perPage,
                                                               @RequestParam(defaultValue = "name") String sort,
                                                               @RequestParam(defaultValue = "DESC") String order) {
        Page<Product> products = productService.getProductsByCategory(categoryId, filter, page, perPage, sort, order);
        return ResponseEntity.ok(products);
    }

    //admin
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }


    //admin
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @GetMapping("/{productId}/related")
    public ResponseEntity<List<Product>> getRelatedProducts(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getRelatedProducts(productId));
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }

    @GetMapping("/ids")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "{}") String ids) {
        return ResponseEntity.ok(productService.getProductsByIds(ids));
    }

    @GetMapping("/top-7-newest")
    public ResponseEntity<?> findFirst3ProductByCateId(){
        return productService.find7ProductNewestByCateId();
    }

    //admin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted");
    }
}
