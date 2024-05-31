package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.Request.ImportInvoiceRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Size;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Warehouse;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.WarehouseService;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    private WarehouseRepository warehouseRepository;
    private ProductRepository productRepository;
    private ColorRepository colorRepository;
    private SizeRepository sizeRepository;
    private UserRepository userRepository;


    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, ProductRepository productRepository, ColorRepository colorRepository, SizeRepository sizeRepository, UserRepository userRepository) {
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Page<Warehouse> getAllWarehouse(String filter, int page, int perPage, String sortBy, String order) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("DESC"))
            direction = Sort.Direction.DESC;

        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterJson.has("importPrice")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("importPrice"), "%" + filterJson.get("importPrice").asText() + "%"));
            }
            if (filterJson.has("createdAt")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("createdAt"), "%" + filterJson.get("createdAt").asText() + "%"));
            }
            return predicate;
        };
        if (sortBy.equals("createdAt")) {
            return warehouseRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "createdAt")));
        }
        if (sortBy.equals("importPrice")) {
            return warehouseRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "importPrice")));
        }

        return warehouseRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, sortBy)));
    }

    @Override
    public List<Warehouse> saveImportInvoices(List<ImportInvoiceRequest> importInvoiceRequests) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Warehouse> importInvoices = new ArrayList<>();
        for (ImportInvoiceRequest importInvoiceRequest : importInvoiceRequests) {
            Warehouse importInvoice = new Warehouse();
            importInvoice.setProduct(productRepository.findById(importInvoiceRequest.getProduct_id()).orElse(null));
            importInvoice.setColor(colorRepository.findById(importInvoiceRequest.getColor_id()).orElse(null));
            importInvoice.setSize(sizeRepository.findById(importInvoiceRequest.getSize_id()).orElse(null));
            importInvoice.setQuantity(importInvoiceRequest.getQuantity());
            importInvoice.setImportPrice(importInvoiceRequest.getImportPrice());
            importInvoice.setCreatedAt(formatter.format(new Date()));
            importInvoice.setCreatedBy(null);
            warehouseRepository.save(importInvoice);
            importInvoices.add(importInvoice);
        }
        return importInvoices;
    }
}
