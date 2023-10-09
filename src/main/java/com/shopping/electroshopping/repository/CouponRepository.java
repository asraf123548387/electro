package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
//    Optional<Coupon> findByCode(String code);

@Query
        ("SELECT u FROM Coupon u WHERE u.code LIKE :code%")
    List<Coupon> findByCodeNAme(String code);

    Coupon findByCode(String couponCode);
}
