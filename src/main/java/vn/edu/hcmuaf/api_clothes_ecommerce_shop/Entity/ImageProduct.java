package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "image_product")
public class ImageProduct {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @Column(name = "product_id")
    private Product product;
    @Column(name = "link")
    private String link;
}
