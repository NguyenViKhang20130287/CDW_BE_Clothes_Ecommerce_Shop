package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String email;
    private String username;
    private String password;
    private int isAdmin;
    private String status;
    private String otp;
}
