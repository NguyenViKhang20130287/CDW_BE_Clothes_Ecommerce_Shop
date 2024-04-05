//package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.EmailConfig;
//import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.OTPConfig;
//import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
//import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
//import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.AuthService;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/v1/auth/")
//public class AuthController {
//
//    private AuthService authService;
//    private EmailConfig emailConfig;
//    private OTPConfig otpConfig;
//    private final Map<String, String> mapOTP = new HashMap<>();
////    @Value(${jwt.secret})
//    private String jwtSecret;
//
//    @Autowired
//    public AuthController(AuthService authService, EmailConfig emailConfig, OTPConfig otpConfig) {
//        this.authService = authService;
//        this.emailConfig = emailConfig;
//        this.otpConfig = otpConfig;
//    }

//    @PostMapping("register")
//    public ResponseEntity<?> register(@RequestParam String email) {
//        User user = authService.findByEmail(email);
//        if (user != null) return new ResponseEntity<>("Email already exists!!!", HttpStatus.BAD_REQUEST);
//        otpConfig.clearOtp(mapOTP, email);
//        String otp = otpConfig.generateOtp(mapOTP, email);
//        emailConfig.send("REGISTER ACCOUNT", email, otp);
//        otpConfig.setTimeOutOtp(mapOTP, email, 3);
//        return new ResponseEntity<>("OTP is sent. The OTP is only valid for 3 minutes!", HttpStatus.OK);
//    }
//
//    @PostMapping("register/confirm")
//    public ResponseEntity<?> registerConfirm(@RequestBody UserDTO userDTO){
//        if (!otpConfig.checkEmailIsValid(mapOTP, userDTO.getEmail()))
//            return new ResponseEntity<>("OTP has expired!!!", HttpStatus.BAD_REQUEST);
//        if (!mapOTP.get(userDTO.getEmail()).equals(userDTO.getOtp()))
//            return new ResponseEntity<>("OTP invalid!!!", HttpStatus.BAD_REQUEST);
//        authService.register(userDTO);
//        otpConfig.clearOtp(mapOTP, userDTO.getEmail());
//        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
//    }
//}
