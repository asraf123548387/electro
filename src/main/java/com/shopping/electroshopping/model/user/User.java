package com.shopping.electroshopping.model.user;


import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.model.role.Role;
import lombok.*;

import javax.persistence.*;
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
    @Column(name = "email")
    private String email;
    @Column (name="password")
    private String password;
    @Column(name="phone_number")
    private String phoneNumber;
    private boolean isBlocked;

    private String otp;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UserAddress> addresses;
    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Cart> carts;


    public Long getId() {
        return id;
    }



    public User(String userName, String email, String password,String phoneNumber, Collection<Role> roles) {
        this.userName = userName;
        this.email = email;
        this.password = password;
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




}
