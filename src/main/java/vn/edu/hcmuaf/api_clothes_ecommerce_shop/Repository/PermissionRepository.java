package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findById(long id);
    Optional<Permission> findByName(String name);
    List<Permission> findAll();
}
