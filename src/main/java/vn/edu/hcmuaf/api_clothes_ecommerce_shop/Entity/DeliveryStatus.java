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
    private long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "deliveryStatus")
    private List<DeliveryStatusHistory> deliveryStatusHistories;
}
