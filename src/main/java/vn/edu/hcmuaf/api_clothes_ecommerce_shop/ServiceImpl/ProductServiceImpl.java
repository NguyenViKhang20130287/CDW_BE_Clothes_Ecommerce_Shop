package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ProductService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ColorSizeRepository colorSizeRepository;
    private ColorRepository colorRepository;
    private SizeRepository sizeRepository;
    private ImageProductRepository imageProductRepository;

    private UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            ColorSizeRepository colorSizeRepository,
            ColorRepository colorRepository,
            SizeRepository sizeRepository,
            ImageProductRepository imageProductRepository,
            UserRepository userRepository
    ) {
        this.productRepository = productRepository;
        this.colorSizeRepository = colorSizeRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.imageProductRepository = imageProductRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsStatusTrue() {
        return productRepository.findByStatusTrue();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Product> getAllProducts(String filter, int page, int perPage, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterJson.has("name")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + filterJson.get("name").asText() + "%"));
            }
            if (filterJson.has("price")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("price"), filterJson.get("price").asDouble()));
            }
            if (filterJson.has("status")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filterJson.get("status").asBoolean()));
            }
            if (filterJson.has("categoryId")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category").get("id"), filterJson.get("categoryId").asLong()));
            }
            return predicate;
        };

        if (sortBy.equals("price")) {
            return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "price")));
        }
        if (sortBy.equals("name")) {
            return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "name")));
        }
        if (sortBy.equals("status")) {
            return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "status")));
        }

        return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, sortBy)));
    }

    @Override
    public Page<Product> getProductsByCategory(Long categoryId, int page, int perPage, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;
        return productRepository.findAllByCategoryId(categoryId, PageRequest.of(page, perPage, Sort.by(direction, sortBy)));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> sortProduct(int pageNum, String sortBy, String orderBy) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (orderBy.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(pageNum, 10, sort);
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ColorSize> colorSizes = new ArrayList<>();
        product.setPrice(product.getPrice());
        product.setCreatedAt(formatter.format(new Date()));
        product.setCreatedBy(userRepository.findById(1L).orElse(null));
        product.setUpdatedBy(userRepository.findById(1L).orElse(null));
        product.setUpdatedAt(formatter.format(new Date()));

        if (product.getThumbnail() == null) {
            product.setThumbnail("");
        }
        if (product.getImageProducts() == null) {
            product.setImageProducts(new ArrayList<>());
        }

        List<ImageProduct> imageProducts = new ArrayList<>();
        for (ImageProduct imageProduct : product.getImageProducts()) {
            imageProduct.setProduct(product);
            imageProduct.setLink(imageProduct.getLink());
            imageProductRepository.save(imageProduct);
            imageProducts.add(imageProductRepository.save(imageProduct));
        }
        product.setImageProducts(imageProducts);

        Product newProduct = productRepository.save(product);

        for (ColorSize colorSize : product.getColorSizes()) {
            colorSize.setProduct(newProduct);
            colorSize.setColor(colorRepository.findById(colorSize.getColor().getId()).orElse(null));
            colorSize.setSize(sizeRepository.findById(colorSize.getSize().getId()).orElse(null));
            colorSize.setQuantity(0);
            colorSizeRepository.save(colorSize);
            colorSizes.add(colorSize);
        }
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(long productId, Product productUpdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Product exitingProduct = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        exitingProduct.setName(productUpdate.getName());
        exitingProduct.setPrice(productUpdate.getPrice());
        exitingProduct.setThumbnail(productUpdate.getThumbnail());
        exitingProduct.setContent(productUpdate.getContent());
        exitingProduct.setCategory(productUpdate.getCategory());
        exitingProduct.setUpdatedAt(formatter.format(new Date()));
        exitingProduct.setUpdatedBy(userRepository.findById(1L).orElse(null));
        exitingProduct.setStatus(productUpdate.isStatus());

        List<ColorSize> updatedColorSizes = new ArrayList<>();
        for (ColorSize updatedColorSize : productUpdate.getColorSizes()) {
            ColorSize exitingColorSize = exitingProduct.getColorSizes().stream().filter(v -> v.getId() == updatedColorSize.getId()).findFirst().orElse(null);
            if (exitingColorSize != null) {
                exitingColorSize.setColor(colorRepository.findById(updatedColorSize.getColor().getId()).orElse(null));
                exitingColorSize.setSize(sizeRepository.findById(updatedColorSize.getSize().getId()).orElse(null));
                exitingColorSize.setQuantity(updatedColorSize.getQuantity());
                colorSizeRepository.save(exitingColorSize);
                updatedColorSizes.add(colorSizeRepository.save(exitingColorSize));
            } else {
                ArrayList<ColorSize> colorSizes = new ArrayList<>();
                for (ColorSize colorSize : productUpdate.getColorSizes()) {
                    colorSize.setProduct(productUpdate);
                    colorSize.setColor(colorRepository.findById(colorSize.getColor().getId()).orElse(null));
                    colorSize.setSize(sizeRepository.findById(colorSize.getSize().getId()).orElse(null));
                    colorSize.setQuantity(0);
                    colorSizeRepository.save(colorSize);
                    colorSizes.add(colorSize);
                }
            }

        }


        return productRepository.save(exitingProduct);
    }

    @Override
    public List<Product> getRelatedProducts(long productId) {
        Product currentProduct = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Long categoryId = currentProduct.getCategory().getId();

        Pageable pageable = PageRequest.of(0, 4); // Get the first 4 products
        return productRepository.findRelatedProducts(categoryId, productId, pageable);
    }

}
