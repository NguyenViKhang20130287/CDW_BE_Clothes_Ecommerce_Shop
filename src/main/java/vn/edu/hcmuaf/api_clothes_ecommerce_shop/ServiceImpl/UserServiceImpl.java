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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserInformationRepository userInformationRepository) {
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
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
            if (jsonFilter.has("q")){
                String searchStr = jsonFilter.get("q").asText();
                Join<User, UserInformation> userJoinUserInfo = root.join("user");
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
}
