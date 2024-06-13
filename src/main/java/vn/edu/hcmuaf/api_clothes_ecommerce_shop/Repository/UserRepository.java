package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Specification<User> specification, Pageable pageable);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findById(long id);
    Optional<User> findByUserInformationEmail(String email);
    Optional<User> findByUsernameOrUserInformationEmail(String username, String email);
    List<User> findAllByStatus(boolean status);
}
