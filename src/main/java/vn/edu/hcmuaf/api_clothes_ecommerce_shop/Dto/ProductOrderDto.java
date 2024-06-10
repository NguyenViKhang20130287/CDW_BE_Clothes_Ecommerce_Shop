package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDto {
    private long id;
    private String productName;
    private String size;
    private String color;
    private int quantity;
    private double price;
    private long colorSizeId;
}
