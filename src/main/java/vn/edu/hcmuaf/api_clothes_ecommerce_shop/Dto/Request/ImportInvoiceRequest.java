package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.Request;

import lombok.*;

import java.sql.Date;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportInvoiceRequest {
    private long product_id;
    private long color_id;
    private long size_id;
    private double importPrice;
    private int quantity;
    private String createdAt;
}
