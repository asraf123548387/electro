package com.shopping.electroshopping.model.product;

import com.shopping.electroshopping.model.cart.CartItems;
import com.shopping.electroshopping.model.category.Category;
//import com.shopping.electroshopping.model.productOffer.ProductOffer;
import com.shopping.electroshopping.model.productOffer.ProductOffer;
import com.shopping.electroshopping.model.wishlist.WishListItem;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")

    private Long Id;
    @Column(name = "product_name")
    private String productName;
    @Column (name = "price")
    private double price;
    @Column(name = "description")
    private String description;
    @Column(name="stock")
    private Integer stock;
    @OneToMany(mappedBy = "product")
    private List<CartItems> cartItems;
    @OneToMany(mappedBy = "product")
    private List<WishListItem> wishListItems;
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    private Category category;
    @Column(name = "image_name")
    private String imageName;
   @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
   private List<ProductOffer> offers;

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", category=" + (category != null ? category.getName() : null) + // Avoid circular reference
                ", imageName='" + imageName + '\'' +
                '}';
    }

}
