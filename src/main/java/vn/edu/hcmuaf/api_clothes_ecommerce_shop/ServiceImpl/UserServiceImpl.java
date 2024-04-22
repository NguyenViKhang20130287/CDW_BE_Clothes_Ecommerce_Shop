package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Image.ImageBBService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.*;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.UserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

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


    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserInformationRepository userInformationRepository,
            PasswordEncoder passwordEncoder,
            PermissionRepository permissionRepository,
            ReviewRepository reviewRepository,
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            ImageBBService imageBBService
    ) {
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.imageBBService = imageBBService;
    }

    @Override
    public Page<User> findAll(int page, int size, String sort, String order, String filter) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sortPa;
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

            System.out.println("User data: " + userDTO.getAvatar());

            String generatePassword = String.format("%04d", (int) (Math.random() * 1000000));
            System.out.println("Password generate: " + generatePassword);

            System.out.println("Permission: " + userDTO.getPermission());
            Permission permission = permissionRepository.findById(userDTO.getPermission()).orElse(null);

            System.out.println("loading...");
//            byte[] imgBytes = userDTO.getAvatar().getBytes();

            byte[] imgBytes = userDTO.getAvatar().getBytes();
            String base64String = imageBBService.convertByteArrayToBase64(imgBytes);
            String imgUrl = imageBBService.uploadImage(base64String);

//            String imgUrl = imageBBService.uploadImage(imgBytes);
            System.out.println("IMG URL: " + imgUrl);

            UserInformation userInfo = new UserInformation();
            userInfo.setFullName(userDTO.getFullName());
            userInfo.setEmail(userDTO.getEmail());
            userInfo.setPhone(userDTO.getPhone());
            userInfo.setAddress(userDTO.getAddress());
            userInfo.setAvatar(imgUrl);
            userInfo.setCreatedAt(LocalDateTime.now());
            userInformationRepository.save(userInfo);
            System.out.println("Create user info successful");

            user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(generatePassword));
            user.setUserInformation(userInfo);
            user.setPermission(permission);
            user.setStatus(true);
            userRepository.save(user);
            System.out.println("Create user successful");

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> edit(long id, UserDTO userDTO) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            String generatePassword = String.format("%04d", (int) (Math.random() * 1000000));
            System.out.println("Password generate: " + generatePassword);
            System.out.println("Permission: " + userDTO.getPermission());
            Permission permission = permissionRepository.findById(userDTO.getPermission()).orElse(null);
            if (userDTO.getAvatar() != null){
                byte[] imgBytes = userDTO.getAvatar().getBytes();
                String base64String = imageBBService.convertByteArrayToBase64(imgBytes);
                String imgUrl = imageBBService.uploadImage(base64String);
                user.getUserInformation().setAvatar(imgUrl);
            }

            user.getUserInformation().setEmail(userDTO.getEmail());
            user.getUserInformation().setFullName(userDTO.getFullName());
            user.getUserInformation().setPhone(userDTO.getPhone());
            user.getUserInformation().setAddress(userDTO.getAddress());
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
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        UserInformation userInfo = user.getUserInformation();
        List<Review> review = user.getReviews();
        reviewRepository.deleteAll(review);
        System.out.println("Delete all reviews success");

        List<Order> orders = user.getOrders();
        for (Order order : orders) {
            orderDetailRepository.deleteAll(order.getOrderDetails());
        }
        orderRepository.deleteAll(orders);
        System.out.println("Delete all order success");

        user.setUserInformation(null);
        userRepository.delete(user);
        System.out.println("Delete user success");
        if (userInfo != null) {
            userInformationRepository.delete(userInfo);
        }

        return new ResponseEntity<>("Delete user has id: " + id + " successful", HttpStatus.OK);
    }


}
