package com.shopping.electroshopping.model.order;

import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.user.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "total_amount")
    private int totalAmount;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "status")
    private String status;
    @Column(name = "order_month")
    private int orderMonth;
    @Column(name = "order_year")
    private int orderYear;
    @OneToOne
    @JoinColumn(name="address_id")
    private UserAddress userAddress;




}
