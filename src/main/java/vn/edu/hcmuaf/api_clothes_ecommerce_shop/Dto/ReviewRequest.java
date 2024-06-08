package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private long userId;
    private long productId;
    private String content;
    private int stars;
    private long orderDetailId;
}
