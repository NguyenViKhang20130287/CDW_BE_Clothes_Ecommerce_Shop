package vn.edu.hcmuaf.api_clothes_ecommerce_shop.DaoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao.ProductDao;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;

@Repository
public class ProductDaoImpl implements ProductDao {
    private EntityManager entityManager;

    @Autowired
    public ProductDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Product findById(long id) {
        Product product = entityManager.find(Product.class, id);
        return product;
    }
}
