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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "color_code")
    private String colorCode;
    @JsonIgnore
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<ColorSize> colorSizes;

//    @JsonIgnore
//    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
//    private List<OrderDetails> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<ImportWarehouseDetail> importWarehouseDetails;

    @Column(name = "is_deleted")
    private boolean isDeleted;

}
