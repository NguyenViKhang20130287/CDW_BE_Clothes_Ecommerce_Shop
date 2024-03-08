package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "payment_method")
    private String payment_method;
    @Column(name = "payment_status")
    private int payment_status;
    @Column(name = "total_amount")
    private double total_amount;
    @Column(name = "discount_code_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_code_id", nullable = true)
    private DiscountCode discountCode;
    @Column(name = "delivery_status_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_status_id", nullable = false)
    private DeliveryStatus deliveryStatus;
    @Column(name = "shipping_cost")
    private double shipping_cost;
    @Column(name = "created_at")
    private String created_at;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
}
