package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.UserInformationRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.UserRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserInformationRepository userInformationRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserInformationRepository userInformationRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserInformation> findAll(int page, int size, String sort, String order, String filter) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sortPa = Sort.by(direction, sort);
        Pageable pageable = PageRequest.of(page, size, sortPa);

        JsonNode jsonFilter;
        try {
            jsonFilter = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Specification<UserInformation> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (jsonFilter.has("status")) {
                boolean userStatus = jsonFilter.get("status").asBoolean();
                Join<User, UserInformation> userJoinUserInfo = root.join("user");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userJoinUserInfo.get("status"), userStatus));
            }
            if (jsonFilter.has("q")) {
                String searchStr = jsonFilter.get("q").asText();
                predicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + searchStr.toLowerCase() + "%");
            }
//                predicate = criteriaBuilder.and(predicate,
//                        criteriaBuilder.or(
//                                criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + filter.toLowerCase() + "%"),
//                                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + filter.toLowerCase() + "%"),
//                                criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + filter.toLowerCase() + "%"),
//                                criteriaBuilder.like(criteriaBuilder.lower(root.get("status")), "%" + filter.toLowerCase() + "%")
//                        )
//                );
            return predicate;
        };

        return userInformationRepository.findAll(specification, pageable);
    }

    @Override
    public List<UserInformation> findAll() {
        return userInformationRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserInformation findByEmail(String email) {
        return userInformationRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserInformation findByEmailOrUsername(String email, String username) {
        return userInformationRepository.findByEmailOrUserUsername(email, username).orElse(null);
    }

    @Override
    public ResponseEntity<?> createNew(UserDTO userDTO) {
        UserInformation userCheck = findByEmailOrUsername(userDTO.getEmail(), userDTO.getUsername());
        if (userCheck != null) return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);
        String generatePassword = String.format("%04d", (int) (Math.random() * 1000000));
        System.out.println("Password generate: " + generatePassword);

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(generatePassword));
        user.setIsAdmin(userDTO.getIsAdmin());
        user.setStatus(true);
        userRepository.save(user);
        System.out.println("Create user successful");

        UserInformation userInfo = new UserInformation();
        userInfo.setEmail(userDTO.getEmail());
        userInfo.setAddress(userDTO.getAddress());
        userInfo.setFullName(userDTO.getFullName());
        userInfo.setPhone(userDTO.getPhone());
        userInfo.setAvatar(null);
        userInfo.setUser(user);
        userInformationRepository.save(userInfo);
        System.out.println("Create user info successful");

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @Override
    public UserInformation findByUserId(long id) {
        return userInformationRepository.findByUserId(id).orElse(null);
    }
}
