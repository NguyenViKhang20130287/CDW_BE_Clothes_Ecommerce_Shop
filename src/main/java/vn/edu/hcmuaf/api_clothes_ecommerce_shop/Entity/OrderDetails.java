package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    @Column(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Column(name = "product_name")
    private String product_name;
    @Column(name = "size_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Size size;
    @Column(name = "color_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Color color;
    @Column(name = "quantity")
    private int quantity;
}
