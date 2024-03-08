package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name="user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(name="product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Column(name="content")
    private String content;
    @Column(name="score")
    private int score;
    @Column(name="created_at")
    private String created_at;

}
