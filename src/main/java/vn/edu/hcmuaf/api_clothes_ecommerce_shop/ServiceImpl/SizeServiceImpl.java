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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Size;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.SizeRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.SizeService;

import java.nio.charset.StandardCharsets;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;


    @Override
    public Page<Size> getAllSize(String filter, int start, int end, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Size> specification = (root, query, criteriaBuilder) -> {
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
            return sizeRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, "name")));
        }

        return sizeRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, sortBy)));
    }

    @Override
    public Size getSizeById(Long id) {
        return sizeRepository.findById(id).orElse(null);
    }

    @Override
    public Size addSize(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public Size updateSize(Long id, Size size) {
        Size sizeToUpdate = sizeRepository.findById(id).orElse(null);
        if (sizeToUpdate == null) {
            return null;
        }
        sizeToUpdate.setName(size.getName());
        return sizeRepository.save(sizeToUpdate);
    }

    @Override
    public void deleteSize(Long id) {
        Size sizeToDelete = sizeRepository.findById(id).orElse(null);
        sizeToDelete.setDeleted(true);
        sizeRepository.save(sizeToDelete);
    }
}
