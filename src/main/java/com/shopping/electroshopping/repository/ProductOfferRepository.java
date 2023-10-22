package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.productOffer.ProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferRepository extends JpaRepository<ProductOffer,Long> {
}
