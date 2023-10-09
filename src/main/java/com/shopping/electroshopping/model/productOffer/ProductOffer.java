//package com.shopping.electroshopping.model.productOffer;
//
//import com.shopping.electroshopping.model.product.Product;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import javax.persistence.*;
//import javax.validation.constraints.FutureOrPresent;
//import javax.validation.constraints.PositiveOrZero;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "product_offer")
//public class ProductOffer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long productOfferId;
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    private double discountAmount;
//
//    private String expirationDate;
//    private String createDate;
//
//    public Long getProductId() {
//        if (product != null) {
//            return product.getId();
//        }
//        return null; // or handle the null case as needed
//    }
//
//}
