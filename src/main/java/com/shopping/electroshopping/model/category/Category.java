package com.shopping.electroshopping.model.category;

import com.shopping.electroshopping.model.categoryOffer.CategoryOffer;
import com.shopping.electroshopping.model.product.Product;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long Id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<CategoryOffer> Offers;

    public Category(String name) {
    }
}
