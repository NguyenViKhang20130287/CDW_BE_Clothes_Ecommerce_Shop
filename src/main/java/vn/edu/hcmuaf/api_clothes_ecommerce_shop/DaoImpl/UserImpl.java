package vn.edu.hcmuaf.api_clothes_ecommerce_shop.DaoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao.UserDao;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.User;

import java.util.List;

@Repository
public class UserImpl implements UserDao {

    private EntityManager entityManager;

    @Autowired
    public UserImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<User> getAll(int pageNum, int pageSize, String sort, String order) {
        Session session = entityManager.unwrap(Session.class);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        //
        int startIndex = (pageNum - 1) * pageSize;

        criteriaQuery.orderBy(
          order.equalsIgnoreCase("desc")
                ? builder.desc(root.get(sort))
                  : builder.asc(root.get(sort))
        );

        //
        Query<User> query = session.createQuery(criteriaQuery)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize);
        return query.getResultList();
    }
}
