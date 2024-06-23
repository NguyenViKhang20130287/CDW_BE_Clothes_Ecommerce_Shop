package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.EmailConfig;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.JwtService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.AddressDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Image.ImageBBService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.LogService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.UserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserInformationRepository userInformationRepository;
    private PasswordEncoder passwordEncoder;
    private PermissionRepository permissionRepository;
    private ReviewRepository reviewRepository;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private EmailConfig emailConfig;
    private ImageBBService imageBBService;
    private JwtService jwtService;
    private AddressRepository addressRepository;
    private LogService logService;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserInformationRepository userInformationRepository,
            PasswordEncoder passwordEncoder,
            PermissionRepository permissionRepository,
            ReviewRepository reviewRepository,
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            ImageBBService imageBBService,
            JwtService jwtService,
            AddressRepository addressRepository,
            LogService logService
    ) {
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.imageBBService = imageBBService;
        this.jwtService = jwtService;
        this.addressRepository = addressRepository;
        this.logService = logService;
    }

    @Override
    public Page<User> findAll(int page, int size, String sort, String order, String filter) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sortPa;
        if (sort.equalsIgnoreCase("id")) {
            sortPa = Sort.by(direction, "id");
        }
        if (sort.equalsIgnoreCase("createdAt")) {
            sortPa = Sort.by(direction, "createdAt");
        }
        if (sort.equalsIgnoreCase("fullName")) {
            sortPa = Sort.by(direction, "userInformation.fullName");
        } else {
            sortPa = Sort.by(direction, sort);
        }
        Pageable pageable = PageRequest.of(page, size, sortPa);

        JsonNode jsonFilter;
        try {
            jsonFilter = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Specification<User> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (jsonFilter.has("status")) {
                boolean userStatus = jsonFilter.get("status").asBoolean();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), userStatus));
            }
            if (jsonFilter.has("q")) {
                String searchStr = jsonFilter.get("q").asText();
                Join<User, UserInformation> userInformationJoin = root.join("userInformation");
                predicate = criteriaBuilder.like(criteriaBuilder.lower(userInformationJoin.get("fullName")), "%" + searchStr.toLowerCase() + "%");
            }
            return predicate;
        };

        return userRepository.findAll(specification, pageable);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByUserInformationEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmailOrUsername(String username, String email) {
        return userRepository.findByUsernameOrUserInformationEmail(username, email).orElse(null);
    }

    @Override
    public ResponseEntity<?> createNew(UserDTO userDTO) {
        try {
            User user = findByEmailOrUsername(userDTO.getUsername(), userDTO.getEmail());
            if (user != null) return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);

            String generatePassword = String.format("%04d", (int) (Math.random() * 1000000));
            System.out.println("Password generate: " + generatePassword);

            System.out.println("Permission: " + userDTO.getPermission());
            Permission permission = permissionRepository.findById(userDTO.getPermission()).orElse(null);

            UserInformation userInfo = new UserInformation();
            userInfo.setFullName(userDTO.getFullName());
            userInfo.setEmail(userDTO.getEmail());
            userInfo.setPhone(userDTO.getPhone());
            userInfo.setAvatar(userDTO.getAvatarLink());
            userInfo.setCreatedAt(LocalDateTime.now());
            userInformationRepository.save(userInfo);
            System.out.println("Create user info successful");

            user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(generatePassword));
            user.setUserInformation(userInfo);
            user.setPermission(permission);
            user.setStatus(true);
            user.setCreatedAt(String.valueOf(LocalDateTime.now()));
            userRepository.save(user);
            System.out.println("Create user successful");

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> edit(long id, UserDTO userDTO) {
        System.out.println("Data: " + userDTO);
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            String generatePassword = String.format("%04d", (int) (Math.random() * 1000000));
            System.out.println("Password generate: " + generatePassword);
//            emailConfig.sendNewPassword(user.getUserInformation().getEmail(), generatePassword);
            System.out.println("Permission: " + userDTO.getPermission());
            Permission permission = permissionRepository.findById(userDTO.getPermission()).orElse(null);
            if (userDTO.getAvatarLink() != null) {
                user.getUserInformation().setAvatar(userDTO.getAvatarLink());
            }

            user.getUserInformation().setEmail(userDTO.getEmail());
            user.getUserInformation().setFullName(userDTO.getFullName());
            user.getUserInformation().setPhone(userDTO.getPhone());
            user.getUserInformation().setUpdatedAt(LocalDateTime.now());
            user.setPermission(permission);
            user.setStatus(userDTO.isStatus());
            userRepository.save(user);
            System.out.println("Edit user has id: " + id + " success");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> delete(long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            user.setStatus(false);
            userRepository.save(user);
            return new ResponseEntity<>("Delete user has id: " + id + " successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed action", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> loadDataUser(String token) {

        if (token == null) return new ResponseEntity<>("Token expired !", HttpStatus.BAD_REQUEST);
        try {
            Claims claims = jwtService.decode(token);
            String username = claims.getSubject();
            User user = findByUsername(username);
            if (user == null) return new ResponseEntity<>("User not found !", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Token expired !", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> editUser(UserDTO userDTO) {
        User user = findByUsername(userDTO.getUsername());
        if (user == null) return new ResponseEntity<>("Tài khoản không tồn tại !", HttpStatus.BAD_REQUEST);
        String fullName = userDTO.getFullName();
        String email = userDTO.getEmail();
        String avatar = userDTO.getAvatarLink();
        String phone = userDTO.getPhone();
        user.getUserInformation().setUpdatedAt(LocalDateTime.now());
        user.getUserInformation().setEmail(email);
        user.getUserInformation().setFullName(fullName);
        user.getUserInformation().setPhone(phone);
        user.getUserInformation().setAvatar(avatar);
        userRepository.save(user);
        System.out.println("Edit user: " + user.getUsername() + " success");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addNewAddress(String username, AddressDTO addressDTO) {
        try {
            User user = findByUsername(username);
            if (addressDTO.isDefault()) {
                List<Address> addresses = user.getAddresses();
                for (Address ar : addresses) {
                    ar.setDefault(false);
                }
            }
            userRepository.save(user);
            Address address = new Address();
            address.setUser(user);
            address.setFullName(addressDTO.getFullName());
            address.setPhone(addressDTO.getPhone());
            address.setStreet(addressDTO.getStreet());
            address.setWardId(addressDTO.getWardId());
            address.setWard(addressDTO.getWard());
            address.setDistrictId(addressDTO.getDistrictId());
            address.setDistrict(addressDTO.getDistrict());
            address.setProvinceId(addressDTO.getProvinceId());
            address.setProvince(addressDTO.getProvince());
            if (addressDTO.isDefault()) {
                List<Address> addresses = user.getAddresses();
                for (Address ar : addresses) {
                    ar.setDefault(false);
                }
            }
            address.setDefault(addressDTO.isDefault());
            address.setCreatedAt(String.valueOf(LocalDateTime.now()));
            addressRepository.save(address);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi thao tác !", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> changePassword(UserDTO userDTO) {
        User user = findByUsername(userDTO.getUsername());
        if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
            user.getUserInformation().setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            return new ResponseEntity<>("Thay đổi mật khẩu thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Mật khẩu không chính xác !", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> editAddress(String username, AddressDTO addressDTO) {
        try {
            User user = findByUsername(username);
            if (addressDTO.isDefault()) {
                List<Address> addresses = user.getAddresses();
                for (Address ar : addresses) {
                    ar.setDefault(false);
                }
            }
            userRepository.save(user);
            Address address = addressRepository.findById(addressDTO.getId()).orElse(null);
            if (address == null) return new ResponseEntity<>("Address not found!", HttpStatus.BAD_REQUEST);
            address.setFullName(addressDTO.getFullName());
            address.setPhone(addressDTO.getPhone());
            address.setStreet(addressDTO.getStreet());
            address.setWardId(addressDTO.getWardId());
            address.setWard(addressDTO.getWard());
            address.setDistrictId(addressDTO.getDistrictId());
            address.setDistrict(addressDTO.getDistrict());
            address.setProvinceId(addressDTO.getProvinceId());
            address.setProvince(addressDTO.getProvince());
            address.setDefault(addressDTO.isDefault());
            address.setUpdatedAt(String.valueOf(LocalDateTime.now()));
            addressRepository.save(address);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi thao tác !", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> loadAddressUser(String token) {
        if (token == null) return new ResponseEntity<>("Token expired !", HttpStatus.BAD_REQUEST);
        try {
            Claims claims = jwtService.decode(token);
            String username = claims.getSubject();
            User user = findByUsername(username);
            if (user == null) return new ResponseEntity<>("User not found !", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(user.getAddresses(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Token expired !", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> loadOrdersUser(String token) {
        try {
            Claims claims = jwtService.decode(token);
            String username = claims.getSubject();
            User user = findByUsername(username);
            if (user == null) return new ResponseEntity<>("User not found !", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Token expired", HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> setDefaultAddress(long userId, long addressId) {
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;
        List<Address> addresses = user.getAddresses();
        for (Address address : addresses) {
            address.setDefault(address.getId() == addressId);
            addressRepository.save(address);
        }
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @Override
    public List<User> getAllUsers(String ids) {
        JsonNode filterJson;
        try {
            filterJson = new ObjectMapper().readTree(java.net.URLDecoder.decode(ids, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if (filterJson.has("ids")) {
            List<Long> idsList = new ArrayList<>();
            for (JsonNode idNode : filterJson.get("ids")) {
                idsList.add(idNode.asLong());
            }
            Iterable<Long> itr = List.of(Stream.of(idsList).flatMap(List::stream).toArray(Long[]::new));
            return userRepository.findAllById(itr);
        }
        return null;
    }

}
