package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_promotion")
public class ProductPromotion {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Column(name = "promotion_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Promotion promotion;
}
