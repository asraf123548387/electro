package com.shopping.electroshopping.service.couponService;

import com.shopping.electroshopping.dto.CouponDto;
import com.shopping.electroshopping.model.coupon.Coupon;
import com.shopping.electroshopping.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CouponServiceImpl {
    @Autowired
    CouponRepository couponRepository;
    public Coupon addCoupon(CouponDto couponDto) {
        Coupon coupon=new Coupon();
        coupon.setCode(couponDto.getCode());
        coupon.setDiscountPercentage(couponDto.getDiscountPercentage());
        coupon.setExpirationDate(couponDto.getExpirationDate());
        return couponRepository.save(coupon);
    }

    public List<Coupon> getCodeByName(String code) {
        return couponRepository.findByCodeNAme(code);
    }

    public void editCoupon(Long id, CouponDto couponDto) {
        Coupon coupon=couponRepository.findById(id).orElse(null);
        if(coupon!=null)
        {
            coupon.setCode(coupon.getCode());
            coupon.setExpirationDate(coupon.getExpirationDate());
            coupon.setDiscountPercentage(couponDto.getDiscountPercentage());

        }
        couponRepository.save(coupon);
    }
}
