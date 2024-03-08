package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    @Column(name="quantity")
    private int quantity;
}
