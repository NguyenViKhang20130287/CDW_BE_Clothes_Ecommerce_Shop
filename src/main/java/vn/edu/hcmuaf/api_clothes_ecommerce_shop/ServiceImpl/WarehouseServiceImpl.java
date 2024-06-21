package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.Request.ImportInvoiceDetailRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.Request.ImportInvoiceRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ImportWarehouseDetailRepository;
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
    private ImportWarehouseDetailRepository importWarehouseDetailRepository;
    private ColorSizeRepository colorSizeRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, ProductRepository productRepository, ColorRepository colorRepository, SizeRepository sizeRepository, UserRepository userRepository, ImportWarehouseDetailRepository importWarehouseDetailRepository, ColorSizeRepository colorSizeRepository) {
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.userRepository = userRepository;
        this.importWarehouseDetailRepository = importWarehouseDetailRepository;
        this.colorSizeRepository = colorSizeRepository;
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
            if (filterJson.has("q")) {
                String searchStr = filterJson.get("q").asText();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("createdAt")), "%" + searchStr.toLowerCase() + "%"));
            }
            if (filterJson.has("createdAt")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("createdAt"), "%" + filterJson.get("createdAt").asText() + "%"));
            }
            return predicate;
        };
        if (sortBy.equals("createdAt")) {
            return warehouseRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, "createdAt")));
        }

        return warehouseRepository.findAll(specification, PageRequest.of(page, perPage, Sort.by(direction, sortBy)));
    }

    @Override
    public Warehouse saveImportInvoices(ImportInvoiceRequest importInvoiceRequest) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Warehouse importInvoice = new Warehouse();
        importInvoice.setCreatedAt(formatter.format(new Date()));
        importInvoice.setCreatedBy(importInvoiceRequest.getCreatedBy());
        double totalPrice = 0;
        warehouseRepository.save(importInvoice);
        List<ImportWarehouseDetail> importWarehouseDetails = new ArrayList<>();
        for (ImportInvoiceDetailRequest importInvoiceDetailRequest : importInvoiceRequest.getImportInvoiceDetailRequests()) {
            Product product = productRepository.findById(importInvoiceDetailRequest.getProduct_id()).orElse(null);
            Color color = colorRepository.findById(importInvoiceDetailRequest.getColor_id()).orElse(null);
            Size size = sizeRepository.findById(importInvoiceDetailRequest.getSize_id()).orElse(null);
            ImportWarehouseDetail importWarehouseDetail = new ImportWarehouseDetail();
            importWarehouseDetail.setProduct(product);
            importWarehouseDetail.setColor(color);
            importWarehouseDetail.setSize(size);
            importWarehouseDetail.setQuantity(importInvoiceDetailRequest.getQuantity());
            importWarehouseDetail.setImportPrice(importInvoiceDetailRequest.getImportPrice());
            importWarehouseDetail.setWarehouse(importInvoice);
            importWarehouseDetailRepository.save(importWarehouseDetail);
            importWarehouseDetails.add(importWarehouseDetail);
            totalPrice += importWarehouseDetail.getImportPrice() * importWarehouseDetail.getQuantity();

            // Update quantity in color_size table
            ColorSize colorSize = colorSizeRepository.findByColorIdAndSizeIdAndProductId(color.getId(), size.getId(), product.getId());
            if (colorSize == null) {
                colorSize = new ColorSize();
                colorSize.setColor(color);
                colorSize.setSize(size);
                colorSize.setProduct(product);
                colorSize.setQuantity(importInvoiceDetailRequest.getQuantity());
            } else {
                colorSize.setQuantity(colorSize.getQuantity() + importInvoiceDetailRequest.getQuantity());
            }
            colorSizeRepository.save(colorSize);
            //update quantity in product table
            product.setQuantity(product.getQuantity() + importInvoiceDetailRequest.getQuantity());
            productRepository.save(product);

        }
        importInvoice.setTotalAmount(totalPrice);
        importInvoice.setImportWarehouseDetails(importWarehouseDetails);
        warehouseRepository.save(importInvoice);
        return importInvoice;
    }
}
