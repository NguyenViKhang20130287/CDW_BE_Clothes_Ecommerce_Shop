package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Slider;

import java.util.List;


public interface SliderService {
    Page<Slider> getAllSliders(String filter, int start, int end, String sortBy, String order);
    Slider createSlider(Slider slider);
    Slider updateSlider(long id, Slider slider);
    Slider getSliderById(long id);
    void deleteSlider(long id);

}
