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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Slider;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.SliderRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.SliderService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class SliderServiceImpl implements SliderService {
    @Autowired
    private SliderRepository sliderRepository;

    @Override
    public Page<Slider> getAllSliders(String filter, int start, int end, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Slider> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterJson.has("q")) {
                String searchStr = filterJson.get("q").asText();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + searchStr.toLowerCase() + "%"));
            }
            if (filterJson.has("status")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), filterJson.get("status").asBoolean()));
            }
            if (filterJson.has("isDeleted")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isDeleted"), filterJson.get("isDeleted").asBoolean()));
            }
            return predicate;
        };
        if (sortBy.equals("id")) {
            return sliderRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, "id")));
        }
        if (sortBy.equals("description")) {
            return sliderRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, "description")));
        }
        if (sortBy.equals("status")) {
            return sliderRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, "status")));
        }
        return sliderRepository.findAll(specification, PageRequest.of(start, end, Sort.by(direction, sortBy)));
    }

    @Override
    public Slider createSlider(Slider slider) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        slider.setCreatedAt(formatter.format(System.currentTimeMillis()));
        slider.setUpdatedAt(formatter.format(System.currentTimeMillis()));
        return sliderRepository.save(slider);
    }

    @Override
    public Slider updateSlider(long id, Slider slider) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Slider sliderToUpdate = sliderRepository.findById(id).orElseThrow(() -> new RuntimeException("Slider not found"));
        sliderToUpdate.setDescription(slider.getDescription());
        sliderToUpdate.setLink(slider.getLink());
        sliderToUpdate.setStatus(slider.isStatus());
        sliderToUpdate.setUpdatedAt(formatter.format(System.currentTimeMillis()));
        sliderToUpdate.setUpdatedBy(slider.getUpdatedBy());
        return sliderRepository.save(sliderToUpdate);
    }

    @Override
    public Slider getSliderById(long id) {
        return sliderRepository.findById(id).orElseThrow(() -> new RuntimeException("Slider not found"));
    }

    @Override
    public void deleteSlider(long id) {
        Slider slider = sliderRepository.findById(id).orElseThrow(() -> new RuntimeException("Slider not found"));
        slider.setDeleted(true);
        sliderRepository.save(slider);
    }
}
