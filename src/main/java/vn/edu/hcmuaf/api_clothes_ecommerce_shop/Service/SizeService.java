package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Size;

public interface SizeService {
    Page<Size> getAllSize(String filter, int start, int end, String sortBy, String order);
}