package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Color;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.ColorSize;

public interface ColorSizeService {
    Page<ColorSize> getAllColorSize(String filter, int start, int end, String sortBy, String order);

    ColorSize getColorSizeByColorIdAndSizeIdAndProductId(Long colorId, Long sizeId, Long productId);
}
