package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao;

import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Slider;

import java.util.List;

public interface SliderDao {
    List<Slider> findAll();
    List<Slider> activeSlider();
}
