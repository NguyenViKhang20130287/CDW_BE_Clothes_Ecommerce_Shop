package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportInvoiceDetailRequest {
    private long product_id;
    private long color_id;
    private long size_id;
    private double importPrice;
    private int quantity;

}
