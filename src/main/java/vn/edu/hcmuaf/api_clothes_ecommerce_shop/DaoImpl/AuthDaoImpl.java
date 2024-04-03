package vn.edu.hcmuaf.api_clothes_ecommerce_shop.DaoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao.AuthDao;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dto.UserDTO;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.UserInformation;

import java.time.LocalDateTime;
import java.util.Collections;

@Repository
public class AuthDaoImpl implements AuthDao {

    private EntityManager entityManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthDaoImpl(EntityManager entityManager, PasswordEncoder passwordEncoder) {
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        String query = "from UserInformation where email = :emailInput";
        TypedQuery<UserInformation> userTypedQuery = entityManager.createQuery(query, UserInformation.class);
        userTypedQuery.setParameter("emailInput", email);
        if (userTypedQuery.getResultList().isEmpty()) return null;
        return userTypedQuery.getSingleResult().getUser();
    }

    @Override
    public User findByUsername(String username) {
        String query = "from User where username = :usernameInput";
        TypedQuery<User> userTypedQuery = entityManager.createQuery(query, User.class);
        userTypedQuery.setParameter("usernameInput", username);
        if (userTypedQuery.getResultList().isEmpty()) return null;
        return userTypedQuery.getSingleResult();
    }

    @Override
    public String getRole(User user) {
        if (user.getIsAdmin() == 0) return "ADMIN";
        return "CUSTOMER";
    }

    @Override
    public User register(UserDTO userDTO) {
        UserInformation userInformation = new UserInformation();
        userInformation.setEmail(userDTO.getEmail());
        userInformation.setCreatedAt(LocalDateTime.now());
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setIsAdmin(1);
        user.setStatus(1);
        entityManager.persist(userInformation);
        entityManager.persist(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password !!!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(getRole(user))));
    }
}
