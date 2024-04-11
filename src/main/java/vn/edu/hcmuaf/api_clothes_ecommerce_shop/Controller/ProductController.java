package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ProductService;

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
    ){
        return productService.sortProduct(pageNum, sortBy, orderBy);
    }
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "{}") String filter,
                                                        @RequestParam(defaultValue = "25") int perPage,
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

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
       return ResponseEntity.ok(productService.createProduct(product));
    }
}
