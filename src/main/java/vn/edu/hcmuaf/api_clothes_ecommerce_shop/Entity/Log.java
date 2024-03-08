package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "time_stamp")
    private String time_stamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "action")
    private String action;
}
