package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.AddressDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;

import java.util.List;

public interface UserService {
    Page<User> findAll(int page, int size, String sort, String order, String filter);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByEmailOrUsername(String email, String username);
    User findById(long id);
    ResponseEntity<?> createNew(UserDTO userDTO);
    ResponseEntity<?> edit(long id, UserDTO userDTO);
    ResponseEntity<?> delete(long id);
    ResponseEntity<?> loadDataUser(String token);
    ResponseEntity<?> editUser(UserDTO userDTO);
    ResponseEntity<?> addNewAddress(String username, AddressDTO addressDTO);
    ResponseEntity<?> changePassword(UserDTO userDTO);

}
