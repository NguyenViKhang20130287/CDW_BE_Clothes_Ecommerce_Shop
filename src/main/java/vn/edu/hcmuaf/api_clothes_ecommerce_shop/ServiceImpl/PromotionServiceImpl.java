package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.PromotionDto;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Promotion;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.ProductRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.PromotionRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.PromotionService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    private PromotionRepository promotionRepository;

    private ProductRepository productRepository;
    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Page<PromotionDto> getAllPromotion(String filter, int page, int perPage, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Promotion> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterJson.has("q")) {
                String searchStr = filterJson.get("q").asText();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchStr.toLowerCase() + "%"));
            }
            if (filterJson.has("discount_rate")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("discount_rate"), filterJson.get("discount_rate").asLong()));
            }
            if (filterJson.has("status")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filterJson.get("status").asBoolean()));
            }
            if (filterJson.has("start_date")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("start_date").get("id"), filterJson.get("start_date").asText()));
            }
            if (filterJson.has("end_date")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("end_date").get("id"), filterJson.get("end_date").asText()));
            }
            if (filterJson.has("isDeleted")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isDeleted"), filterJson.get("isDeleted").asBoolean()));
            }
            return predicate;
        };

        return switch (sortBy) {
            case "id" ->
                    promotionRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "id"))).map(PromotionDto::from);
            case "name" ->
                    promotionRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "name"))).map(PromotionDto::from);
            case "status" ->
                    promotionRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "status"))).map(PromotionDto::from);
            default ->
                    promotionRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, sortBy))).map(PromotionDto::from);
        };
    }

    @Override
    public PromotionDto getPromotionById(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion == null) {
            return null;
        }
        return PromotionDto.from(promotion);
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        promotion.setCreatedAt(formatter.format(new java.util.Date()));
        promotion.setUpdatedAt(formatter.format(new java.util.Date()));
        List<Product> products = new ArrayList<>();
        if(promotion.getProducts() != null) {
            for (Product product : promotion.getProducts()) {
                Product existingProduct = productRepository.findById(product.getId()).orElse(null);
                if (existingProduct != null) {
                    existingProduct.getPromotions().add(promotion);
                    products.add(existingProduct);
                }
            }
        }
        promotion.setProducts(products);
        promotion.setDeleted(false);
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updatePromotion(long id, Promotion promotion) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Promotion existingPromotion = promotionRepository.findById(id).orElse(null);
        if (existingPromotion == null) {
            return null;
        }
        existingPromotion.setName(promotion.getName());
        existingPromotion.setDescription(promotion.getDescription());
        existingPromotion.setDiscount_rate(promotion.getDiscount_rate());
        existingPromotion.setStartDate(promotion.getStartDate());
        existingPromotion.setEndDate(promotion.getEndDate());
        existingPromotion.setStatus(promotion.isStatus());
        existingPromotion.setUpdatedAt(formatter.format(new java.util.Date()));
        existingPromotion.setUpdatedBy(promotion.getUpdatedBy());

        List<Product> products = new ArrayList<>();
        if (promotion.getProducts() != null) { // Check if getProducts() is not null
            for (Product product : promotion.getProducts()) {
                Product existingProduct = productRepository.findById(product.getId()).orElse(null);
                if (!existingPromotion.getProducts().contains(existingProduct)) {
                    existingPromotion.getProducts().add(existingProduct);
                    existingProduct.getPromotions().add(existingPromotion);
                }
                products.add(existingProduct);
            }
        }
        List<Product> productsToRemove = new ArrayList<>();
        for (Product existingProduct : existingPromotion.getProducts()) {
            if (!products.contains(existingProduct)) {
                productsToRemove.add(existingProduct);
                existingProduct.getPromotions().remove(existingPromotion);
            }
        }
        existingPromotion.getProducts().removeIf(product -> !products.contains(product));
        existingPromotion.setProducts(products);
        return promotionRepository.save(existingPromotion);
    }

    @Override
    public List<Promotion> getPromotionsByIds(List<Long> ids) {
        return promotionRepository.findAllByIds(ids);
    }

    @Override
    public void deletePromotion(Long id) {
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        promotion.setDeleted(true);
        promotionRepository.save(promotion);
    }
}
