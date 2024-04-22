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
    private final Map<String, String> mapOTP = new HashMap<>();

    @Override
    public UserInformation findByEmail(String email) {
        return userInformationRepository.findByEmail(email).orElse(null);
    }

    @Override
    public ResponseEntity<?> register(String email) {
        UserInformation userInformation = findByEmail(email);
        if (userInformation != null)
            return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);

        otpConfig.clearOtp(mapOTP, email);
        String otp = otpConfig.generateOtp(mapOTP, email);
        emailConfig.send("REGISTER ACCOUNT", email, otp);
        otpConfig.setTimeOutOtp(mapOTP, email, 3);
        return new ResponseEntity<>("Email sent successful", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> registerConfirm(UserDTO userDTO) {
        if (!otpConfig.checkEmailIsValid(mapOTP, userDTO.getEmail()))
            return new ResponseEntity<>("OTP has expired!!!", HttpStatus.BAD_REQUEST);
        if (!mapOTP.get(userDTO.getEmail()).equals(userDTO.getOtp()))
            return new ResponseEntity<>("OTP invalid!!!", HttpStatus.BAD_REQUEST);
        User userCheck = userRepository.findByUsername(userDTO.getUsername()).orElse(null);
        if (userCheck != null) return new ResponseEntity<>("Username already exist", HttpStatus.BAD_REQUEST);

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
                .build();
        userRepository.save(user);

        otpConfig.clearOtp(mapOTP, userDTO.getEmail());
        var jwtToken = jwtService.generateToken(user);
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
                var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return new ResponseEntity<>(AuthenticationResponse
                        .builder()
                        .token(jwtToken)
                        .build(),
                        HttpStatus.OK);
            }

        }
        return new ResponseEntity<>("Username or password incorrect", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> forgot(String email) {
        User user = userRepository.findByUserInformationEmail(email).orElse(null);
        if (user == null)
            return new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
        otpConfig.clearOtp(mapOTP, email);
        String otp = otpConfig.generateOtp(mapOTP, email);
        emailConfig.send("RESET PASSWORD", email, otp);
        otpConfig.setTimeOutOtp(mapOTP, email, 3);
        return new ResponseEntity<>("Email sent successful", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> reset(UserDTO userDTO) {
        if (!otpConfig.checkEmailIsValid(mapOTP, userDTO.getEmail()))
            return new ResponseEntity<>("OTP has expired!!!", HttpStatus.BAD_REQUEST);
        if (!mapOTP.get(userDTO.getEmail()).equals(userDTO.getOtp()))
            return new ResponseEntity<>("OTP invalid!!!", HttpStatus.BAD_REQUEST);

        User user = userRepository.findByUserInformationEmail(userDTO.getEmail()).orElse(null);
        assert user != null;
        user.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
        userRepository.save(user);
        otpConfig.clearOtp(mapOTP, userDTO.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return new ResponseEntity<>(
                AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build(),
                HttpStatus.OK);
    }
}
