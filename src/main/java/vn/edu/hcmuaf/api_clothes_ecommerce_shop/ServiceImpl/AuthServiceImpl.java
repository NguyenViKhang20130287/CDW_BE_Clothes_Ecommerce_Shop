//package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
//import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
//import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Repository.AuthRepository;
//import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.AuthService;
//
//@Service
//public class AuthServiceImpl implements AuthService {
//    @Autowired
//    private AuthRepository authRepository;
//
//    @Override
//    public User findByEmail(String email) {
//        return authRepository.findByEmail(email);
//    }
//
//    @Override
//    public User findByUsername(String username) {
//        return authRepository.findByUsername(username);
//    }
//
//    @Override
//    public User register(UserDTO userDTO) {
//        return authRepository.register(userDTO);
//    }

//    private AuthDao authDao;
//
//    @Autowired
//    public AuthServiceImpl(AuthDao authDao){
//        this.authDao = authDao;
//    }
//
//    @Override
//    public User findByEmail(String email) {
//        return authDao.findByEmail(email);
//    }
//
//    @Override
//    public User findByUsername(String username) {
//        return authDao.findByUsername(username);
//    }
//
//    @Override
//    public User register(UserDTO userDTO) {
//        return authDao.register(userDTO);
//    }
//}
