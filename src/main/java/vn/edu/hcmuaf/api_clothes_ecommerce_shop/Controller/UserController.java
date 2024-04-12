package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.UserService;

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
    public Page<UserInformation> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int perPage,
            @RequestParam(defaultValue = "fullName") String sort,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String filter
    ) {
        return userService.findAll(page, perPage, sort, order, filter);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(userService.findAll());
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
            UserInformation userResult = userService.findByEmail(email);
            return ResponseEntity.ok(userResult);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find-by-email-or-username")
    public ResponseEntity<?> findByEmailOrUsername(
            @RequestParam String email,
            @RequestParam String username
    ) {
        return ResponseEntity.ok(userService.findByEmailOrUsername(email, username));
    }

    // create new user
    @PostMapping("")
    public ResponseEntity<?> createNewUser(
            @RequestBody UserDTO userDTO
    ) {
        return ResponseEntity.ok(userService.createNew(userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return ResponseEntity.ok(userService.findByUserId(id));
    }
}
