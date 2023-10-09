package com.shopping.electroshopping.model.categoryOffer;

import com.shopping.electroshopping.model.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category_offer")
public class CategoryOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOfferId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @PositiveOrZero(message = "Discount amount must be non-negative")
    private double discountAmount;
    @FutureOrPresent(message = "Expiration date is must be in the future or present")
    private LocalDate expirationDate;
    @CreatedDate
    private LocalDateTime createdDate;


}
