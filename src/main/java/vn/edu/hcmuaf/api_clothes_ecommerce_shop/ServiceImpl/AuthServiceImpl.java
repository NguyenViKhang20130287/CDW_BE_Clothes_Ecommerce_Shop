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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.UserInformationRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.UserRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.AuthService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.auth.AuthenticationRequest;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.auth.AuthenticationResponse;

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

        var user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .isAdmin(1)
                .status(1)
                .build();
        userRepository.save(user);
//        UserInformation userInfo = new UserInformation();
//        userInfo.setEmail(userInfo.getEmail());
        var userInfo = UserInformation.builder()
                .user(user)
                .fullName(null)
                .email(userDTO.getEmail())
                .build();
        userInformationRepository.save(userInfo);
        //
        otpConfig.clearOtp(mapOTP, userDTO.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return new ResponseEntity<>(
                AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build(),
                HttpStatus.OK);
    }

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("Invalid email or password !!!");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                Collections.singleton(
//                        new SimpleGrantedAuthority(user.getIsAdmin() == 0 ? "ROLE_ADMIN" : "ROLE_CUSTOMER"
//                        )
//                )
//        );
//    }
}
