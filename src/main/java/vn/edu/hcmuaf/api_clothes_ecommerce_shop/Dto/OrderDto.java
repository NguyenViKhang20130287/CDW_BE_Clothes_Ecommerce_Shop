package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private long userId;
    private String fullName;
    private String address;
    private String phone;
    private String paymentMethod;
    private boolean paymentStatus;
    private double totalAmount;
    private String discountCode;
    private int deliveryStatusId;
    private double shippingCost;
    private List<ProductOrderDto> products;
//    private long productId;
//    private String productName;
//    private int sizeId;
//    private int colorId;
//    private int quantity;
//    private double price;
}
