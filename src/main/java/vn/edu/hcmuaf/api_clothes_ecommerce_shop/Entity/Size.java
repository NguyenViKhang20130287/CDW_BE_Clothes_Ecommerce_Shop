package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "size")
public class Size {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    private List<ColorSize> colorSizes;

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    private List<Warehouse> warehouses;

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

}
