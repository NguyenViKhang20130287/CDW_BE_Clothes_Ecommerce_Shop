package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Permission;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.PermissionRepository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.PermissionService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    private PermissionRepository repository;
    @Autowired
    public PermissionServiceImpl(PermissionRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Permission> findAll() {
        return repository.findAll();
    }
}
