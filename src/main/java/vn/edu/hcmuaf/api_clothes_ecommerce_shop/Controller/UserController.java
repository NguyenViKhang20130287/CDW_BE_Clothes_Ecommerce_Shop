package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    // create new user
    @PostMapping("")
    public ResponseEntity<?> createNewUser(
            @ModelAttribute UserDTO userDTO
    ) {
        return ResponseEntity.ok(userService.createNew(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(
            @PathVariable long id,
            @ModelAttribute UserDTO userDTO
    ) {
        return ResponseEntity.ok(userService.edit(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable long id
    ) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/user-details")
    public ResponseEntity<?> loadDataUser(
            @RequestParam String token
    ) {
        return userService.loadDataUser(token);
    }

}
