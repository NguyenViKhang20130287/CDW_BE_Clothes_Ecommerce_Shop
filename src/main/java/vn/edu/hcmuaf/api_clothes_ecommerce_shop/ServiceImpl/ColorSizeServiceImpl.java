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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Color;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.ColorSize;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.ColorSizeRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ColorSizeService;

import java.nio.charset.StandardCharsets;

@Service
public class ColorSizeServiceImpl implements ColorSizeService {
    ColorSizeRepository colorSizeRepository;
    @Autowired
    public ColorSizeServiceImpl(ColorSizeRepository colorSizeRepository) {
        this.colorSizeRepository = colorSizeRepository;
    }
    @Override
    public Page<ColorSize> getAllColorSize(String filter, int start, int end, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<ColorSize> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterJson.has("name")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("quantity"), "%" + filterJson.get("quantity").asInt() + "%"));
            }
            return predicate;
        };

        if (sortBy.equals("quantity")) {
            return colorSizeRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, "quantity")));
        }

        return colorSizeRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, sortBy)));
    }

    @Override
    public ColorSize getColorSizeByColorIdAndSizeIdAndProductId(Long colorId, Long sizeId, Long productId) {
        return colorSizeRepository.findByColorIdAndSizeIdAndProductId(colorId, sizeId, productId);
    }
}
