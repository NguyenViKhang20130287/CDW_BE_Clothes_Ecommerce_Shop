package vn.edu.hcmuaf.api_clothes_ecommerce_shop.DaoImpl;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao.SliderDao;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Slider;

import java.util.List;

@Repository
public class SliderDaoImpl implements SliderDao {
    private EntityManager entityManager;
    @Autowired
    public SliderDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Slider> findAll() {
        return entityManager.createQuery("from Slider", Slider.class).getResultList();
    }

    @Override
    public List<Slider> activeSlider() {
        return entityManager.createQuery("from Slider where status = 1", Slider.class).getResultList();
    }
}
