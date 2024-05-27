package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String fullName;
    private String phone;
    private String street;
    private String ward;
    private String district;
    private String province;
    private boolean isDefault;

}
