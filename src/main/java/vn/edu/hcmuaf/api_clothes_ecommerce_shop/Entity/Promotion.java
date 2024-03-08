package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "discount_rate")
    private int discount_rate;
    @Column(name = "start_date")
    private String start_date;
    @Column(name = "end_date")
    private String end_date;
    @Column(name = "status")
    private int status;
    @Column(name = "created_at")
    private String created_at;
    @Column(name = "updated_at")
    private String updated_at;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
    private List<ProductPromotion> productPromotions;
}
