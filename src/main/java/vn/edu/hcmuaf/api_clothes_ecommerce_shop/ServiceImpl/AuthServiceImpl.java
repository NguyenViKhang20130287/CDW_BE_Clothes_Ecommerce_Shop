package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.EmailConfig;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.JwtService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.OTPConfig;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Permission;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.PermissionRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.UserInformationRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.UserRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.AuthService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.LogService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.auth.AuthenticationRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.auth.AuthenticationResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserInformationRepository userInformationRepository;
    private final EmailConfig emailConfig;
    private final OTPConfig otpConfig;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PermissionRepository permissionRepository;
    private final LogService logService;
    private final Map<String, String> mapOTP = new HashMap<>();

    @Override
    public UserInformation findByEmail(String email) {
        return userInformationRepository.findByEmail(email).orElse(null);
    }

    @Override
    public ResponseEntity<?> register(String email) {
        UserInformation userInformation = findByEmail(email);
        if (userInformation != null)
            return new ResponseEntity<>("Email đã được đăng kí!", HttpStatus.BAD_REQUEST);

        otpConfig.clearOtp(mapOTP, email);
        String otp = otpConfig.generateOtp(mapOTP, email);
        emailConfig.send("REGISTER ACCOUNT", email, otp);
        otpConfig.setTimeOutOtp(mapOTP, email, 3);
        return new ResponseEntity<>("Email sent successful", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> registerConfirm(UserDTO userDTO) {
        if (!otpConfig.checkEmailIsValid(mapOTP, userDTO.getEmail()))
            return new ResponseEntity<>("OTP hết hạn!", HttpStatus.BAD_REQUEST);
        if (!mapOTP.get(userDTO.getEmail()).equals(userDTO.getOtp()))
            return new ResponseEntity<>("OTP không hợp lệ!", HttpStatus.BAD_REQUEST);
        User userCheck = userRepository.findByUsername(userDTO.getUsername()).orElse(null);
        if (userCheck != null) return new ResponseEntity<>("Email đã được đăng kí!", HttpStatus.BAD_REQUEST);

        Permission permission = permissionRepository.findByName("CUSTOMER").orElse(null);

        var userInfo = UserInformation.builder()
                .fullName(null)
                .email(userDTO.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
        userInformationRepository.save(userInfo);

        var user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .userInformation(userInfo)
                .permission(permission)
                .status(true)
                .createdAt(String.valueOf(LocalDateTime.now()))
                .build();
        userRepository.save(user);

        otpConfig.clearOtp(mapOTP, userDTO.getEmail());
        var jwtToken = jwtService.generateToken(user);
        logService.addLog(user.getId(), "Đăng kí tài khoản thành công");
        return new ResponseEntity<>(
                AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build(),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> login(AuthenticationRequest authenticationRequest) {
        User userCheck = userRepository.findByUsername(authenticationRequest.getUsername()).orElse(null);
        if (userCheck != null) {
            if (passwordEncoder.matches(authenticationRequest.getPassword(), userCheck.getPassword())) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticationRequest.getUsername(),
                                authenticationRequest.getPassword()
                        )
                );
//                System.out.println("Status: " + userCheck.isStatus());
                if (!userCheck.isStatus()) return new ResponseEntity<>("Tài khoản đã bị khóa !", HttpStatus.BAD_REQUEST)
;//                var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
                var jwtToken = jwtService.generateToken(userCheck);
                logService.addLog(userCheck.getId(), "Đăng nhập thành công");
                return new ResponseEntity<>(AuthenticationResponse
                        .builder()
                        .token(jwtToken)
                        .permission(userCheck.getPermission().getName())
                        .build(),
                        HttpStatus.OK);
            }
        }
        assert userCheck != null;
//        logService.addLog(userCheck.getId(), "Đăng nhập thất bại");
        return new ResponseEntity<>("Username hoặc mật khẩu không đúng !", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> forgot(String email) {
        User user = userRepository.findByUserInformationEmail(email).orElse(null);
        if (user == null)
            return new ResponseEntity<>("Email chưa được đăng kí !", HttpStatus.BAD_REQUEST);
        otpConfig.clearOtp(mapOTP, email);
        String otp = otpConfig.generateOtp(mapOTP, email);
        emailConfig.send("RESET PASSWORD", email, otp);
        otpConfig.setTimeOutOtp(mapOTP, email, 3);
        logService.addLog(user.getId(), "Thực hiện lấy lại mật khẩu");
        return new ResponseEntity<>("Email sent successful", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> reset(UserDTO userDTO) {
        if (!otpConfig.checkEmailIsValid(mapOTP, userDTO.getEmail()))
            return new ResponseEntity<>("OTP hết hạn!", HttpStatus.BAD_REQUEST);
        if (!mapOTP.get(userDTO.getEmail()).equals(userDTO.getOtp()))
            return new ResponseEntity<>("OTP không hợp lệ!", HttpStatus.BAD_REQUEST);

        User user = userRepository.findByUserInformationEmail(userDTO.getEmail()).orElse(null);
        assert user != null;
        user.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
        userRepository.save(user);
        otpConfig.clearOtp(mapOTP, userDTO.getEmail());
        var jwtToken = jwtService.generateToken(user);
        logService.addLog(user.getId(), "Đặt lại mật khẩu mới");
        return new ResponseEntity<>(
                AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build(),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> checkAuth(String token) {
        try {
            boolean isExpired = jwtService.isTokenExpired(token);
            System.out.println("Token is expired: " + isExpired);
            if (isExpired) return new ResponseEntity<>("Token is expired!", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Token is expired!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> checkRole(String token) {
        String username = jwtService.decode(token).getSubject();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user.getPermission(), HttpStatus.OK);
    }
}
