package com.shopping.electroshopping.model.cart;

import com.shopping.electroshopping.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;
    private double total;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
    @OneToMany(mappedBy = "cart")
    private List<CartItems> cartItems;


}



