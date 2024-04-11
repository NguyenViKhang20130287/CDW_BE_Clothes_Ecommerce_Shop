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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorSizeRepository colorSizeRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ImageProductRepository imageProductRepository;


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
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<ColorSize> colorSizes = new ArrayList<>();
        for (ColorSize colorSize : product.getColorSizes()) {
            colorSize.setProduct(product);
            // check color name and size name exist in color_size table, if not, create new color and size with that name
            if (colorRepository.findByName(colorSize.getColor().getName()) == null) {
                Color color = new Color();
                color.setName(colorSize.getColor().getName());
                colorRepository.save(color);
                colorSize.setColor(color);
            }
            if (sizeRepository.findByName(colorSize.getSize().getName()) == null) {
                Size size = new Size();
                size.setName(colorSize.getSize().getName());
                sizeRepository.save(size);
                colorSize.setSize(size);
            }
            colorSizeRepository.save(colorSize);
            colorSizes.add(colorSizeRepository.save(colorSize));
        }
        product.setColorSizes(colorSizes);
        product.setPrice(product.getPrice());
        product.setCreated_at(formatter.format(new Date()));
        product.setCreated_by(product.getCreated_by());
        product.setUpdated_at(formatter.format(new Date()));
        product.setUpdated_by(product.getUpdated_by());

        if(product.getThumbnail() == null) {
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
        return productRepository.save(product);
    }

}
