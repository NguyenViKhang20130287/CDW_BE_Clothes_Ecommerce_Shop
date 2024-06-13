package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

import java.util.List;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportInvoiceRequest {
    private Double totalPrice;
    List<ImportInvoiceDetailRequest> importInvoiceDetailRequests;
    private String createdAt;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User createdBy;
}
