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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Promotion;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.PromotionRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.PromotionService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    private PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
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
            if (filterJson.has("name")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + filterJson.get("name").asText() + "%"));
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
            return predicate;
        };

        return switch (sortBy) {
            case "name" ->
                    promotionRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "name"))).map(PromotionDto::from);
            case "status" ->
                    promotionRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "status"))).map(PromotionDto::from);
            default ->
                    promotionRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, sortBy))).map(PromotionDto::from);
        };
    }

    @Override
    public Promotion getPromotionById(Long id) {
        return promotionRepository.findById(id).orElse(null);
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        promotion.setCreatedAt(formatter.format(new java.util.Date()));
        promotion.setUpdatedAt(formatter.format(new java.util.Date()));
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Promotion newPromotion = promotionRepository.findById(promotion.getId()).orElse(null);
        newPromotion.setName(promotion.getName());
        newPromotion.setDescription(promotion.getDescription());
        newPromotion.setDiscount_rate(promotion.getDiscount_rate());
        newPromotion.setStatus(promotion.isStatus());
        newPromotion.setUpdatedAt(formatter.format(new java.util.Date()));
        return promotionRepository.save(newPromotion);
    }

    @Override
    public List<Promotion> getPromotionsByIds(List<Long> ids) {
        return promotionRepository.findAllByIds(ids);
    }
}
