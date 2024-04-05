package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/find-all")
//    public ResponseEntity<?> getAll(
//            @RequestParam(defaultValue = "1") int pageNum,
//            @RequestParam(defaultValue = "5") int pageSize,
//            @RequestParam(defaultValue = "username") String sort,
//            @RequestParam(defaultValue = "asc") String order
//    ) {
//        return ResponseEntity.ok(userService.getAll(pageNum, pageSize, sort, order));
//    }
}
