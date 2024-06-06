package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResDTO implements Serializable {
    private String status;
    private String message;
    private String url;
}
