package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ProductService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ColorSizeRepository colorSizeRepository;
    private ColorRepository colorRepository;
    private SizeRepository sizeRepository;
    private ImageProductRepository imageProductRepository;

    private UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ColorSizeRepository colorSizeRepository,
            ColorRepository colorRepository,
            SizeRepository sizeRepository,
            ImageProductRepository imageProductRepository,
            UserRepository userRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
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
            if (filterJson.has("q")) {
                String searchStr = filterJson.get("q").asText();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchStr.toLowerCase() + "%"));
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
            if (filterJson.has("createdAt")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("createdAt"), filterJson.get("createdAt").asText()));
            }
            if (filterJson.has("price_lt") || filterJson.has("price_gt")) {
                double priceLt = filterJson.has("price_lt") ? filterJson.get("price_lt").asDouble() : Double.MAX_VALUE;
                double priceGt = filterJson.has("price_gt") ? filterJson.get("price_gt").asDouble() : 0;
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("price"), priceGt, priceLt));
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
        if (sortBy.equals("createdAt")) {
            return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "createdAt")));
        }
        if (sortBy.equals("category.name")) {
            return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "category.name")));
        }
        return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, sortBy)));
    }

    @Override
    public Page<Product> getProductsByCategory(Long categoryId, String filter, int page, int perPage, String sortBy, String order) {
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
            if (filterJson.has("q")) {
                String searchStr = filterJson.get("q").asText();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchStr.toLowerCase() + "%"));
            }
            if (filterJson.has("price")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("price"), filterJson.get("price").asDouble()));
            }
            if (filterJson.has("status")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filterJson.get("status").asBoolean()));
            }
            if (filterJson.has("category")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category").get("id"), filterJson.get("category").asLong()));
            }
            if (filterJson.has("isDeleted")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isDeleted"), filterJson.get("isDeleted").asBoolean()));
            }
            return predicate;
        };
        if (sortBy.equals("price")) {
            return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "price")));
        }
        if (sortBy.equals("name")) {
            return productRepository.findAll( specification, PageRequest.of(page, perPage, Sort.by(direction, "name")));
        }
        if (sortBy.equals("id")) {
            return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "id")));
        }
        if (sortBy.equals("createdAt")) {
            return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "createdAt")));
        }
        return productRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "status")));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setDeleted(true);
        productRepository.save(product);
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
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        product.setCategory(product.getCategory());
        product.setPrice(product.getPrice());
        product.setCreatedAt(formatter.format(new Date()));
        product.setUpdatedAt(formatter.format(new Date()));
        product.setThumbnail(product.getThumbnail());

        if (product.getImageProducts() == null) {
            product.setImageProducts(new ArrayList<>());
        }
//        product = productRepository.save(product);
        List<ImageProduct> imageProducts = new ArrayList<>();
        for (ImageProduct imageProduct : product.getImageProducts()) {
            imageProduct.setLink(imageProduct.getLink());
            imageProduct.setProduct(product);
            imageProductRepository.save(imageProduct);
            imageProducts.add(imageProduct);
        }
        product.setImageProducts(imageProducts);
        Product newProduct = productRepository.save(product);

        for (ColorSize colorSize : product.getColorSizes()) {
            colorSize.setProduct(newProduct);
            if (colorSize.getColor() != null) {
                colorSize.setColor(colorRepository.findById(colorSize.getColor().getId()).orElse(null));
            } else {
               break;
            }
            if (colorSize.getSize() != null) {
                colorSize.setSize(sizeRepository.findById(colorSize.getSize().getId()).orElse(null));
            } else {
                break;
            }
            colorSize.setQuantity(0);
            colorSizeRepository.save(colorSize);
            colorSizes.add(colorSize);
        }
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(long productId, Product productUpdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        existingProduct.setName(productUpdate.getName());
        existingProduct.setPrice(productUpdate.getPrice());
        existingProduct.setThumbnail(productUpdate.getThumbnail());
        existingProduct.setContent(productUpdate.getContent());
        existingProduct.setCategory(productUpdate.getCategory());
        existingProduct.setUpdatedAt(formatter.format(new Date()));
        existingProduct.setUpdatedBy(productUpdate.getUpdatedBy());
        existingProduct.setStatus(productUpdate.isStatus());

        existingProduct.setThumbnail(productUpdate.getThumbnail());

        List<ImageProduct> newImageProducts = new ArrayList<>();

        for (ImageProduct current : productUpdate.getImageProducts()) {
            ImageProduct existingImageProduct = existingProduct.getImageProducts().stream()
                    .filter(i -> i.getLink().equals(current.getLink()))
                    .findFirst()
                    .orElse(null);
            if (existingImageProduct == null) {
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setLink(current.getLink());
                imageProduct.setProduct(existingProduct);
                imageProductRepository.save(imageProduct);
                newImageProducts.add(imageProduct);
            } else {
                newImageProducts.add(existingImageProduct);
            }
        }
        for (ImageProduct existingImageProduct : existingProduct.getImageProducts()) {
            if (!newImageProducts.isEmpty() && newImageProducts.stream()
                    .noneMatch(i ->
                            i.getLink().equals(existingImageProduct.getLink()))) {
                imageProductRepository.delete(existingImageProduct);
            }
        }
        existingProduct.setImageProducts(newImageProducts);

        existingProduct = productRepository.save(existingProduct); // Save the product first

        List<Long> updatedColorSizeIds = productUpdate.getColorSizes().stream()
                .map(cs -> Long.valueOf(cs.getId()))
                .collect(Collectors.toList());

        Iterator<ColorSize> iterator = existingProduct.getColorSizes().iterator();
        while (iterator.hasNext()) {
            ColorSize existingColorSize = iterator.next();
            if (!updatedColorSizeIds.contains(existingColorSize.getId())) {
                iterator.remove();
                colorSizeRepository.delete(existingColorSize);
            }
        }

        for (ColorSize newColorSize : productUpdate.getColorSizes()) {
            if (newColorSize.getId() == 0) {
                newColorSize.setProduct(existingProduct);
                colorSizeRepository.save(newColorSize);
            } else {
                ColorSize existingColorSize = colorSizeRepository.findById(newColorSize.getId()).orElse(null);
                if (existingColorSize != null) {
                    existingColorSize.setColor(newColorSize.getColor());
                    existingColorSize.setSize(newColorSize.getSize());
                    existingColorSize.setQuantity(newColorSize.getQuantity());
                    colorSizeRepository.save(existingColorSize);
                }
            }
        }

        return existingProduct;
    }

    @Override
    public List<Product> getRelatedProducts(long productId) {
        Product currentProduct = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Long categoryId = currentProduct.getCategory().getId();

        Pageable pageable = PageRequest.of(0, 4); // Get the first 4 products
        return productRepository.findRelatedProducts(categoryId, productId, pageable);
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> getProductsByIds(String ids) {
        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(ids, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (filterJson.has("ids")) {
            List<Long> idsList = new ArrayList<>();
            for (JsonNode idNode : filterJson.get("ids")) {
                idsList.add(idNode.asLong());
            }
            Iterable<Long> itr = List.of(Stream.of(idsList).flatMap(List::stream).toArray(Long[]::new));
            return productRepository.findAllById(itr);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> find7ProductNewestByCateId() {
        List<Product> result = productRepository.findTop7ByOrderByCreatedAtDesc();
        if (result.isEmpty()) return new ResponseEntity<>("List is empty !", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
