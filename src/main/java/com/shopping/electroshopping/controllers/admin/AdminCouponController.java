package com.shopping.electroshopping.controllers.admin;

import com.shopping.electroshopping.dto.CouponDto;
import com.shopping.electroshopping.model.coupon.Coupon;
import com.shopping.electroshopping.repository.CouponRepository;
import com.shopping.electroshopping.service.couponService.CouponServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCouponController {
    @Autowired
    private CouponServiceImpl couponService;
    @Autowired
    private CouponRepository couponRepository;
@GetMapping("/couponList")
    public String couponList(Model model)
{
    List<Coupon> couponList=couponRepository.findAll();
    model.addAttribute("coupon",couponList);
    return"couponList";
}
@GetMapping("/addCoupon")
public String addCoupon()
{
    return "addCoupon";
}
    @ModelAttribute("coupon")
    public CouponDto couponDto() {
        return new CouponDto();
    }

    @PostMapping("addCoupon")
    public String addCouponForm(@ModelAttribute("coupon") CouponDto couponDto)
    {
        couponService.addCoupon(couponDto);
     return "redirect:/admin/couponList";
    }

    @GetMapping("/couponSearch")
    public String couponSearch(@RequestParam("code") String code ,Model model)
    {
        List<Coupon> couponList=couponService.getCodeByName(code);
        model.addAttribute("coupon",couponList);

        return "couponList";
    }


    @GetMapping("deleteCoupon/{id}")
    public String deleteCoupon(@PathVariable("id") Long id)
    {
        this.couponRepository.deleteById(id);
        return "redirect:/admin/couponList";
    }
    @GetMapping("updateCoupon/{id}")
    public String updateCoupon(@PathVariable("id") Long id, Model model)
    {
        Coupon coupon=couponRepository.findById(id).orElseThrow(()->new IllegalArgumentException("in valid product Id: "+id));
        model.addAttribute("coupon",coupon);
        model.addAttribute("id",id);
        return "updateCoupon";
    }
    @PostMapping("updateCoupon/{id}")
    public String updateCouponForm(@PathVariable("id") Long id,@ModelAttribute("coupon") CouponDto couponDto)
    {
        couponService.editCoupon(id,couponDto);
        return "redirect:/admin/couponList";
    }



}
