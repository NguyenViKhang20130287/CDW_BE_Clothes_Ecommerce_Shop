package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Log;

public interface LogService {
    void addLog(long userId, String action);
    ResponseEntity<?> addLogApi(String token, String action);
    Page<Log> findAll(int page, int size, String sort, String order, String filter);
}
