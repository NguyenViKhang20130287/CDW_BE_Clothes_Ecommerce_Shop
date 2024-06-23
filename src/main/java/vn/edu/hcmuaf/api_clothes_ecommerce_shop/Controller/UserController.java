package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.AddressDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public Page<User> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int perPage,
            @RequestParam(defaultValue = "fullName") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String filter
    ) {
        return userService.findAll(page, perPage, sort, order, filter);
    }

    @GetMapping("/find-by-username")
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        try {
            User userResult = userService.findByUsername(username);
            return ResponseEntity.ok(userResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find-by-email")
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        try {
            User userResult = userService.findByEmail(email);
            return ResponseEntity.ok(userResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find-by-username-or-email")
    public ResponseEntity<?> findByEmailOrUsername(
            @RequestParam String username,
            @RequestParam String email
    ) {
        return ResponseEntity.ok(userService.findByEmailOrUsername(username, email));
    }

    // ADMIN
    // create new user
    @PostMapping("")
    public ResponseEntity<?> createNewUser(
            @RequestBody UserDTO userDTO
    ) {
        return ResponseEntity.ok(userService.createNew(userDTO));
    }

    // ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(
            @PathVariable long id,
            @RequestBody UserDTO userDTO
    ) {
        return ResponseEntity.ok(userService.edit(id, userDTO));
    }


    // ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(userService.delete(id));
    }

    // ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/ids")
    public ResponseEntity<?> getUsersByIds(@RequestParam(defaultValue = "{}") String ids) {
        List<User> users = userService.getAllUsers(ids);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user-details")
    public ResponseEntity<?> loadDataUser(
            @RequestParam String token
    ) {
        return userService.loadDataUser(token);
    }

    @PutMapping("/user-details/edit")
    public ResponseEntity<?> editUser(
            @RequestBody UserDTO userDTO
    ) {
        return userService.editUser(userDTO);
    }

    @PostMapping("/user-details/add-new-address")
    public ResponseEntity<?> addNewAddress(
            @RequestParam String username,
            @RequestBody AddressDTO addressDTO
    ) {
        return userService.addNewAddress(username, addressDTO);
    }

    @PostMapping("/user-details/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody UserDTO userDTO
    ) {
        return userService.changePassword(userDTO);
    }

    @PostMapping("/user-details/edit-address")
    public ResponseEntity<?> editAddress(
            @RequestParam String username,
            @RequestBody AddressDTO addressDTO
    ) {
        return userService.editAddress(username, addressDTO);
    }

    @GetMapping("/user-details/addresses")
    public ResponseEntity<?> loadAddressUser(@RequestParam String token){
        return userService.loadAddressUser(token);
    }

    @GetMapping("/user-details/orders")
    public ResponseEntity<?> loadOrders(@RequestParam String token){
        return userService.loadOrdersUser(token);
    }

    @PostMapping("/user-details/address/set-default")
    public ResponseEntity<?> setDefaultAddress(@RequestParam long userId, @RequestParam long addressId){
        return userService.setDefaultAddress(userId, addressId);
    }
}
