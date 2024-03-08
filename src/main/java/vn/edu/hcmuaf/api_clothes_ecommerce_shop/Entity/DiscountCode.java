package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "discount_code")
public class DiscountCode {
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
    @Column(name = "discount_money")
    private int discount_money;
    @Column(name = "start_date")
    private String start_date;
    @Column(name = "end_date")
    private String end_date;
    @Column(name = "status")
    private int status;
    @Column(name = "created_at")
    private String created_at;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "updated_at")
    private String updated_at;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

    @OneToMany(mappedBy = "discountCode", cascade = CascadeType.ALL)
    private List<Order> orders;
}
