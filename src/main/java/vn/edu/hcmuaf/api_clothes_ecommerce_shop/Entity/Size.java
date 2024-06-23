package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    private List<ColorSize> colorSizes;

//    @JsonIgnore
//    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
//    private List<OrderDetails> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL)
    private List<ImportWarehouseDetail> importWarehouseDetails;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
