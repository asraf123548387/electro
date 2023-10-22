package com.shopping.electroshopping.model.user;

import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.model.wallet.Wallet;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAddressID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String streetDetails;
    private String cityName;

    private String state;
    private String postalCode;
    private String addressPhoneNumber;
    private boolean defaultAddress;

    @OneToOne(mappedBy = "userAddress", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Order order;

    public void setUser(User user) {
        this.user = user;
    }

    public void setUser(Long userId) {
    }

    public void setStreet(String street) {
    }

    public void setCity(String city) {
    }




}
