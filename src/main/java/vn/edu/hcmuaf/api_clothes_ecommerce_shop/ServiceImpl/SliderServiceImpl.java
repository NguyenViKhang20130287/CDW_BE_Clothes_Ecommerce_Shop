package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao.SliderDao;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Slider;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.SliderService;

import java.util.List;

@Service
public class SliderServiceImpl implements SliderService {
    private SliderDao sliderDao;
    @Autowired
    public SliderServiceImpl(SliderDao sliderDao){
        this.sliderDao = sliderDao;
    }

    @Override
    public List<Slider> findAll() {
        return sliderDao.findAll();
    }

    @Override
    public List<Slider> activeSlider() {
        return sliderDao.activeSlider();
    }

}
