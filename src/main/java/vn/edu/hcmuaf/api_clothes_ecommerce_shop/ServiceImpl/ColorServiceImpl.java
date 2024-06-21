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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Category;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Color;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ColorService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.ColorRepository;

import java.nio.charset.StandardCharsets;


@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Color getColorById(Long id) {
        return colorRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Color> getAllColor(String filter, int start, int end, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Color> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterJson.has("name")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + filterJson.get("name").asText() + "%"));
            }
            if (filterJson.has("isDeleted")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isDeleted"), filterJson.get("isDeleted").asBoolean()));
            }
            return predicate;
        };

        if (sortBy.equals("name")) {
            return colorRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, "name")));
        }

        return colorRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, sortBy)));
    }

    @Override
    public Color addColor(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public Color updateColor(Long id, Color color) {
        Color colorToUpdate = colorRepository.findById(id).orElse(null);
        if (colorToUpdate == null) {
            return null;
        }
        colorToUpdate.setName(color.getName());
        colorToUpdate.setColorCode(color.getColorCode());
        return colorRepository.save(colorToUpdate);
    }

    @Override
    public void deleteColor(Long id) {
        Color colorToDelete = colorRepository.findById(id).orElse(null);
        colorToDelete.setDeleted(true);
        colorRepository.save(colorToDelete);
    }
}
