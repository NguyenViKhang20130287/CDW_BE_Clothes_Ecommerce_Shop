package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCodeDTO {
    private long id;
    private String token;
    private String code;
    private Date startDate;
    private Date endDate;
    private double discountMoney;
    private int discountRate;
    private long quantity;
    private boolean status;
}
