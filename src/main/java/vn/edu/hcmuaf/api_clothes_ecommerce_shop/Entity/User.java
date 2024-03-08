package vn.edu.hcmuaf.api_clothes_ecommerce_shop.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "isAdmin")
    private int isAdmin;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Permission> permissions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Order> ordersCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<Order> ordersUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Category> categoriesCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<Category> categoriesUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Product> productsCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<Product> productsUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<DiscountCode> discountCodesCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<DiscountCode> discountCodesUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<DeliveryStatus> deliveryStatusesCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<DeliveryStatus> deliveryStatusesUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Color> colorsCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<Color> colorsUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Size> sizesCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<Size> sizesUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<ImageProduct> imageProductsCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<ImageProduct> imageProductsUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Warehouse> warehousesCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<Warehouse> warehousesUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Review> reviewsCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<Review> reviewsUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<ProductPromotion> productPromotionsCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<ProductPromotion> productPromotionsUpdated;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsCreated;

    @OneToMany(mappedBy = "updatedBy", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsUpdated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

}
