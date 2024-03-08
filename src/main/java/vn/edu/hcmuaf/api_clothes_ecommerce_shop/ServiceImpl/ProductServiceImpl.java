package vn.edu.hcmuaf.api_clothes_ecommerce_shop.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Dao.ProductDao;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity.Product;
import vn.edu.hcmuaf.api_clothes_ecommerce_shop.Service.ProductService;

@Service
public class ProductServiceImpl  implements ProductService {

    private ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao){
        this.productDao = productDao;
    }

    @Override
    public Product findById(long id) {
        return productDao.findById(id);
    }
}
