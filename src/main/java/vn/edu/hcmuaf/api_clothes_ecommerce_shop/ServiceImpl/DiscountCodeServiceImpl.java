package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Config.JwtService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.DiscountCodeDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.DiscountCode;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Notification;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.DiscountCodeRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.NotificationRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.UserRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.DiscountCodeService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.LogService;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.NotificationService;

import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DiscountCodeServiceImpl implements DiscountCodeService {

    private DiscountCodeRepository codeRepository;
    private JwtService jwtService;
    private UserRepository userRepository;
    private LogService logService;
    private NotificationRepository notificationRepository;
    private NotificationService notificationService;

    @Autowired
    public DiscountCodeServiceImpl(DiscountCodeRepository codeRepository,
                                   JwtService jwtService,
                                   UserRepository userRepository,
                                   LogService logService,
                                   NotificationRepository notificationRepository,
                                   NotificationService notificationService
    ) {
        this.codeRepository = codeRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.logService = logService;
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    @Override
    public ResponseEntity<?> checkDiscountCode(String code) {
        DiscountCode discount = codeRepository.findByCode(code).orElse(null);
        if (discount == null) return new ResponseEntity<>("DiscountCode invalid", HttpStatus.OK);
        Date nowDate = new Date();
        if ((discount.getStartDate().equals(nowDate) || discount.getStartDate().before(nowDate)) &&
                discount.getEndDate().after(nowDate) && discount.isStatus()) {
            if (discount.getQuantity() > 0) {
                return new ResponseEntity<>(discount, HttpStatus.OK);
            } else return new ResponseEntity<>("Out of stock", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Discount invalid", HttpStatus.OK);
        }
    }

    @Override
    public Page<DiscountCode> findAll(int page, int size, String sort, String order, String filter) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sortBy;
        if (sort.equalsIgnoreCase("id")) {
            sortBy = Sort.by(direction, "id");
        } else {
            sortBy = Sort.by(direction, sort);
        }
        Pageable pageable = PageRequest.of(page, size, sortBy);
        JsonNode jsonFilter;
        try {
            jsonFilter = new ObjectMapper().readTree(java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Specification<DiscountCode> specification = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (jsonFilter.has("status")) {
                boolean paymentStatus = jsonFilter.get("status").asBoolean();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), paymentStatus));
            }
//
//            if (jsonFilter.has("q")) {
//                String searchStr = jsonFilter.get("q").asText();
//                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + searchStr.toLowerCase() + "%"));
//            }

            return predicate;
        };

        return codeRepository.findAll(specification, pageable);
    }

    @Override
    public ResponseEntity<?> findById(long id) {
        DiscountCode discountCode = codeRepository.findById(id).orElse(null);
        return discountCode == null ? new ResponseEntity<>("Discount not found", HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(discountCode, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> create(DiscountCodeDTO discountCodeDTO) {
        try {
            String username = jwtService.decode(discountCodeDTO.getToken()).getSubject();
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            DiscountCode discountCode = codeRepository.findByCode(discountCodeDTO.getCode().toUpperCase()).orElse(null);
            if (discountCode != null) return new ResponseEntity<>("Code already exist", HttpStatus.BAD_REQUEST);
            discountCode = new DiscountCode();
            discountCode.setCode(discountCodeDTO.getCode().toUpperCase());
            discountCode.setStartDate(discountCodeDTO.getStartDate());
            discountCode.setEndDate(discountCodeDTO.getEndDate());
            discountCode.setDiscountMoney(discountCodeDTO.getDiscountMoney());
            discountCode.setDiscountRate(discountCodeDTO.getDiscountRate());
            discountCode.setQuantity(discountCodeDTO.getQuantity());
            discountCode.setStatus(discountCodeDTO.isStatus());
            discountCode.setCreatedAt(LocalDateTime.now());
            discountCode.setCreatedBy(user);
            discountCode.setUpdatedAt(LocalDateTime.now());
            discountCode.setUpdatedBy(user);
            codeRepository.save(discountCode);
            logService.addLog(user.getId(), "Thêm mã giảm giá mới");
            if (discountCode.isStatus()) {
                List<User> users = userRepository.findAllByStatus(true);
                String price = (discountCode.getDiscountRate() == 0 ? String.valueOf(discountCode.getDiscountMoney()) :
                        (discountCode.getDiscountRate() + "%"));
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yy");
                String formattedStartDate = dateFormatter.format(discountCode.getStartDate());
                String formattedEndDate = dateFormatter.format(discountCode.getEndDate());
                String content = "Có mã giảm giá " + discountCode.getCode() + " Thời gian sử dụng từ ngày " +
                        formattedStartDate + " đến ngày " + formattedEndDate +
                        ". Với ưu đãi " + price;
                notificationService.sendNotify(users, content);
            }
            System.out.println("Success");
            return new ResponseEntity<>(discountCode, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Token is expired!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> delete(long id) {
        DiscountCode discountCode = codeRepository.findById(id).orElse(null);
        if (discountCode == null) return new ResponseEntity<>("Discount code not found!", HttpStatus.NOT_FOUND);
        List<User> users = userRepository.findAllByStatus(true);
        String content = "Mã giảm giá " + discountCode.getCode() + " đã hết hạn";
        notificationService.sendNotify(users, content);
        codeRepository.delete(discountCode);
        System.out.println("Delete discount success");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> edit(long id, DiscountCodeDTO discountCodeDTO) {
        try {
            DiscountCode discountCode = codeRepository.findById(id).orElse(null);
            if (discountCode == null) return new ResponseEntity<>("Discount code not found!", HttpStatus.NOT_FOUND);
            String username = jwtService.decode(discountCodeDTO.getToken()).getSubject();
            User user = userRepository.findByUsername(username).orElse(null);
            if (user == null) return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
            discountCode.setCode(discountCode.getCode());
            discountCode.setStartDate(discountCodeDTO.getStartDate());
            discountCode.setEndDate(discountCodeDTO.getEndDate());
            discountCode.setDiscountRate(discountCodeDTO.getDiscountRate());
            discountCode.setDiscountMoney(discountCodeDTO.getDiscountMoney());
            discountCode.setStatus(discountCodeDTO.isStatus());
            discountCode.setQuantity(discountCodeDTO.getQuantity());
            discountCode.setUpdatedAt(LocalDateTime.now());
            discountCode.setUpdatedBy(user);
            codeRepository.save(discountCode);
            return new ResponseEntity<>(discountCode, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Token is expired", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> discountIsUsed(long id) {
        DiscountCode discountCode = codeRepository.findById(id).orElse(null);
        if (discountCode == null) return new ResponseEntity<>("Discount code not found!", HttpStatus.NOT_FOUND);
        discountCode.setQuantity(discountCode.getQuantity() - 1);
        codeRepository.save(discountCode);
        System.out.println("'Quantity discount code: " + discountCode.getQuantity());
        return new ResponseEntity<>(discountCode, HttpStatus.OK);
    }

    public static void main(String[] args) {
    }
}
