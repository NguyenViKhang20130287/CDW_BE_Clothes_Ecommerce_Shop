package vn.edu.hcmuaf.api_clothes_ecommerce_shop.DaoImpl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao.CategoryDao;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Category;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    private EntityManager entityManager;

    @Autowired
    public CategoryDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Category findById(long id) {
        Category category = entityManager.find(Category.class, id);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return entityManager.createQuery("from Category", Category.class).getResultList();
    }

    //active category will be status = 1
    @Override
    public List<Category> activeCategory() {
        return entityManager.createQuery("from Category where status = 1", Category.class).getResultList();
    }

    @Override
    public void deleteCategory(long id) {
        Category category = entityManager.find(Category.class, id);
        entityManager.remove(category);
    }


}
