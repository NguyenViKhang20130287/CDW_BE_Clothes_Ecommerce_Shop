package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_status_history")
public class DeliveryStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "delivery_status_id")
    private DeliveryStatus deliveryStatus;

    @Column(name = "created_at")
    private String createdAt;
}
