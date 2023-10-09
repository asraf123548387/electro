package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.model.cart.CartItems;
import com.shopping.electroshopping.model.coupon.Coupon;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.user.UserAddress;
import com.shopping.electroshopping.repository.CartItemsRepository;
import com.shopping.electroshopping.repository.CartRepository;
import com.shopping.electroshopping.repository.CouponRepository;
import com.shopping.electroshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserCouponController {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartItemsRepository cartItemsRepository;
    @Autowired
    CouponRepository couponRepository;
//    @GetMapping("/applyCouponPage")
//    public String applyCouponPage()
//    {
//        return"/coupon/applyCouponPage";
//    }

    @PostMapping("/applyCoupon")
    public String applyCoupon(@ModelAttribute("cart") Cart cart, @RequestParam("couponCode") String couponCode, Model model) {
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);
            Coupon coupon = couponRepository.findByCode(couponCode);
//            if (StringUtils.isBlank(couponCode)) {
//                model.addAttribute("error", "Coupon code is required.");
//                return "your_form_template"; // Return to the form with an error message
//            }

            if (coupon == null) {
                model.addAttribute("error", "Invalid coupon code.");

//                double discountPercentage=coupon.getDiscountPercentage();
                double totalPrice = cartItemsRepository.sumCartItemsPriceByUser(user);
//                double newTotal=totalPrice* (1 - discountPercentage / 100);
                List<UserAddress> addressList = user.getAddresses();
                List<CartItems> cartItems = cartItemsRepository.findByCartUser(user);
                Cart userCart = cartRepository.findByUser(user);
                userCart.setTotal(totalPrice);
                cartRepository.save(userCart);
                model.addAttribute("addresses", addressList);
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("cartItems", cartItems);
                return "/checkOut/checkOut"; // Return to the form with an error message
            }
            String expirationDateStr = coupon.getExpirationDate();
            Date expirationDate = null;
            if (expirationDateStr != null && !expirationDateStr.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as per your actual format
                try {
                    expirationDate = dateFormat.parse(expirationDateStr);
                } catch (ParseException e) {
                    // Handle parsing error if necessary
                    e.printStackTrace();
                }
            }

            // Check if the coupon has expired
            Date currentDate = new Date();
            if (expirationDate != null && currentDate.after(expirationDate)) {
                model.addAttribute("error", "Coupon has expired.");
                // Other error handling or redirection logic if needed
                return "/checkOut/checkOut";
            }
            double discountPercentage=coupon.getDiscountPercentage();
            double totalPrice = cartItemsRepository.sumCartItemsPriceByUser(user);
            double newTotal=totalPrice* (1 - discountPercentage / 100);
            List<UserAddress> addressList = user.getAddresses();
            List<CartItems> cartItems = cartItemsRepository.findByCartUser(user);
            Cart userCart = cartRepository.findByUser(user);
            userCart.setTotal(newTotal);
            cartRepository.save(userCart);
            model.addAttribute("addresses", addressList);
            model.addAttribute("totalPrice", newTotal);
            model.addAttribute("cartItems", cartItems);

            return "/checkOut/checkOut";
        }


    }

}
