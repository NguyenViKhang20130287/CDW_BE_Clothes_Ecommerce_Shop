package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Category;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Color;

public interface ColorService {
    Color getColorById(Long id);
    Page<Color> getAllColor(String filter, int start, int end, String sortBy, String order);

    Color addColor(Color color);
    Color updateColor(Long id, Color color);

    void deleteColor(Long id);
}
