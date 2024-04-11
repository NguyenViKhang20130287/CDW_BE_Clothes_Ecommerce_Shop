package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<ColorSize> colorSizes;

    @JsonIgnore
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<Warehouse> warehouses;

    @JsonIgnore
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
}
