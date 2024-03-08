package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

}
