package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Column(name = "color_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Color color;
    @Column(name = "size_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Size size;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "created_at")
    private String created_at;
    @Column(name = "created_by")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}
