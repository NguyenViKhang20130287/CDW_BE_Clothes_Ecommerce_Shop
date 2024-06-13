package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.Request.ImportInvoiceDetailRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.Request.ImportInvoiceRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Warehouse;

import java.util.List;

public interface WarehouseService {
    Page<Warehouse> getAllWarehouse(String filter, int page, int perPage, String sortBy, String order);
    Warehouse saveImportInvoices(ImportInvoiceRequest importInvoiceRequest);
}
