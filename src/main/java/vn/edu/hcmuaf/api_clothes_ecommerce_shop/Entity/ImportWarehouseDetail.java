package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "import_warehouse_details")
public class ImportWarehouseDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @NotNull(message = "Not empty")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "Not empty")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "color_id")
    private Color color;

    @NotNull(message = "Not empty")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "size_id")
    private Size size;

    @Column(name = "import_price")
    private Double importPrice;

    private int quantity;

}
