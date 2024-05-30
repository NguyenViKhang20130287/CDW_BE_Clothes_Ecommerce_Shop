package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Promotion;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

import java.util.List;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionDto {

    private long id;

    private String name;

    private String description;

    private int discount_rate;

    private boolean status;

    private String startDate;

    private String endDate;

    private String createdDate;

    private String updatedDate;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User createdBy;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User updatedBy;

    private List<Product> products;

    public static PromotionDto from(Promotion promotion) {
        return PromotionDto.builder()
                .id(promotion.getId())
                .name(promotion.getName())
                .description(promotion.getDescription())
                .discount_rate(promotion.getDiscount_rate())
                .status(promotion.isStatus())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .createdDate(promotion.getCreatedAt())
                .updatedDate(promotion.getUpdatedAt())
                .createdBy(promotion.getCreatedBy())
                .updatedBy(promotion.getUpdatedBy())
                .products(promotion.getProducts())
                .build();
    }
}
