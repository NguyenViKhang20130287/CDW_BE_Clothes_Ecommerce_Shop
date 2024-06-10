package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String email;
    private String username;
    private String password;
    private String newPassword;
    private int isAdmin;
    private boolean status;
    private String otp;
    private String fullName;
    private String address;
    private int permission;
    private String phone;
    private String avatarLink;
}
