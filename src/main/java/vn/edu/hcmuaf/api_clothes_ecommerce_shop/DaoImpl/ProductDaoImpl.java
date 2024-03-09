package vn.edu.hcmuaf.api_clothes_ecommerce_shop.DaoImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao.ProductDao;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;

import java.util.List;

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

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public List<Product> activeProduct() {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.status = 1", Product.class).getResultList();
    }

    @Override
    public List<Product> categoryProduct(long id) {
        return entityManager.createQuery("SELECT p FROM Product p WHERE p.category.id = :id", Product.class).setParameter("id", id).getResultList();
    }

}
