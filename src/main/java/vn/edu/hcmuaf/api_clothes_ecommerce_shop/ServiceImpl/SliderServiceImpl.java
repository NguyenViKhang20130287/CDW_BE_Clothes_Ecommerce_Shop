package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Slider;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.SliderRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.SliderService;

import java.util.List;

@Service
public class SliderServiceImpl implements SliderService {
    @Autowired
    private SliderRepository sliderRepository;

}
