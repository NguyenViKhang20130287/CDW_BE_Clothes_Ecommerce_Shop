package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "name")
    private String name;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "content")
    private String content;
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "sold")
    private int sold;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "color_size",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id"))
    private List<Color> colors;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "color_size",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<Size> sizes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ImageProduct> imageProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Warehouse> warehouses;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductPromotion> productPromotions;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;
}
