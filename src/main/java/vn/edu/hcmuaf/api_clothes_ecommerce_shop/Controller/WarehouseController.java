package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.Request.ImportInvoiceRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Warehouse;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")

public class WarehouseController {
    private WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Warehouse>> getAllWarehouse(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "{}") String filter,
                                                           @RequestParam(defaultValue = "25") int perPage,
                                                           @RequestParam(defaultValue = "id") String sort,
                                                           @RequestParam(defaultValue = "DESC") String order) {
        Page<Warehouse> warehouse = warehouseService.getAllWarehouse(filter, page, perPage, sort, order);
        return ResponseEntity.ok(warehouse);
    }

    @PostMapping
    public ResponseEntity<Warehouse> saveImportInvoices(@RequestBody ImportInvoiceRequest importInvoiceRequest) {
        Warehouse warehouse = warehouseService.saveImportInvoices(importInvoiceRequest);
        return ResponseEntity.ok(warehouse);
    }
}
