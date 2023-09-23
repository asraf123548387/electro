package com.shopping.electroshopping.model.product;

import com.shopping.electroshopping.model.cart.CartItems;
import com.shopping.electroshopping.model.category.Category;
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
    @OneToMany(mappedBy = "product")
    private List<CartItems> cartItems;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    private Category category;
    @Column(name = "image_name")
    private String imageName;


    public Product(String productName, double price, String description) {
    }


}
