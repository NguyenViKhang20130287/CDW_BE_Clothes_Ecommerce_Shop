package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private long id;
    private String fullName;
    private String phone;
    private String street;
    private long wardId;
    private String ward;
    private long districtId;
    private String district;
    private long provinceId;
    private String province;
    private boolean isDefault;

}
