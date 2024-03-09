package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Slider;

import java.util.List;


public interface SliderService {
    List<Slider> findAll();
    List<Slider> activeSlider();
}
