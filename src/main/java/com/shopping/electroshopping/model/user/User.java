package com.shopping.electroshopping.model.user;
import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.model.role.Role;
import com.shopping.electroshopping.model.wallet.Wallet;
import com.shopping.electroshopping.model.wishlist.WishListItem;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column (name="user_name")
    private String userName;
    @Column(name = "email",unique = true)
   @NotNull
    private String email;
    @Column (name="password")
    private String password;
    @Column(name="phone_number")
    private String phoneNumber;
    private boolean isBlocked;
    private boolean verified;
    private String otp;

    @Column(name = "referral_code", unique = true)
    private String referralCode;

    @Column(name = "referrer_id")
    private Long referrerId;

    @Column(name = "referral_reward")
    private Integer referralReward;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UserAddress> addresses;
    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Cart> carts;
    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Order> orders;
    @OneToOne(mappedBy = "user",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private  Wallet wallets;
    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private List<WishListItem> wishListItems;



    public Long getId() {
        return id;
    }



    public User(String userName, String email, String password,String phoneNumber,String otp, Collection<Role> roles) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.otp=otp;
        this.phoneNumber=phoneNumber;
        this.roles = roles;

    }
    public User( String email,String otp, Collection<Role> roles) {

        this.email = email;
      this.otp=otp;
        this.roles = roles;
    }
//    @Column(name="address_id")
//    private String addressId;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))

    private Collection<Role> roles;

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + userName + "', email='" + email + "'}";
    }


}
