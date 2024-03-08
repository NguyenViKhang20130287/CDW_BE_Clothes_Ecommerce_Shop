package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "delivery_status")
public class DeliveryStatus {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "deliveryStatus", cascade = CascadeType.ALL)
    private List<Order> orders;
}
