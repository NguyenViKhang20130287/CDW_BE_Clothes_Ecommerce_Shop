package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "slider")
public class Slider {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "link")
    private String link;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private int status;
    @Column(name = "created_at")
    private String created_at;
    @Column(name = "created_by")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    @Column(name = "updated_at")
    private String updated_at;
    @Column(name = "updated_by")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

}
