package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "color_size")
public class ColorSize {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "product_id")
    private List<Product> product;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "color_id")
    private Color color;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "size_id")
    private Size size;
    @Column(name="quantity")
    private int quantity;
}
