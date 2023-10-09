package com.shopping.electroshopping.model.referaloffer;

import com.shopping.electroshopping.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "referralOffer")
public class ReferalOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "referral_id")
    private Long referralId;
    @Column(name = "referrer_id")
    private Long referrerId;
    @Column(name = "referred_customer_id")
    private Long referredCustomerId;
    @ManyToOne
    @JoinColumn(name = "referrer_user_id")
    private User referrer;
    private String status;
    @ManyToOne
    @JoinColumn(name = "referred_customer_user_id")
    private User referredCustomer;

    @Column(name = "discount_amount")
    private double discountAmount;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;


}
