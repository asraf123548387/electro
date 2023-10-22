package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.dto.UserAddressDto;
import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.model.cart.CartItems;
import com.shopping.electroshopping.model.coupon.Coupon;
import com.shopping.electroshopping.model.product.Product;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.user.UserAddress;
import com.shopping.electroshopping.repository.*;
import com.shopping.electroshopping.service.productService.ProductServiceImpl;
import com.shopping.electroshopping.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserCheckOutController {
   @Autowired
   UserRepository userRepository;
   @Autowired
   UserServiceImpl userService;
   @Autowired
   private CouponRepository couponRepository;
   @Autowired
   CartRepository cartRepository;

   @Autowired
   UserAddressRepository userAddressRepository;
   @Autowired
   CartItemsRepository cartItemsRepository;
   @Autowired
   ProductServiceImpl productService;

   @GetMapping("/checkOut")
   public String checkOut(Model model, RedirectAttributes redirectAttributes) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String email = authentication.getName();
      User user = userRepository.findByEmail(email);

      List<UserAddress> addressList = user.getAddresses();
      if (!addressList.isEmpty()) {
         // 3. Set the first address as the default
         UserAddress defaultAddress = addressList.get(0);
         defaultAddress.setDefaultAddress(true);

         // 4. Set all other addresses as not default
         for (UserAddress address : addressList) {
            if (!address.equals(defaultAddress)) {
               address.setDefaultAddress(false);
            }
         }

         // 5. Save the changes to the database
         userAddressRepository.saveAll(addressList);
      }

      List<CartItems> cartItems = cartItemsRepository.findByCartUser(user);

      double totalPrice = cartItemsRepository.sumCartItemsPriceByUser(user);
      Cart userCart = cartRepository.findByUser(user);

      for (CartItems cartItem : cartItems) {
         Product product = cartItem.getProduct();
         int quantityPurchased = cartItem.getQuantity();
         int currentStockQuantity = product.getStock();

         if (currentStockQuantity >= quantityPurchased) {
            // Deduct the purchased quantity from the product's stock
            product.setStock(currentStockQuantity - quantityPurchased);
            productService.saveProduct(product);
         } else {
            // Handle insufficient stock
            // You can return an error message or handle it as needed
            // For example, you can redirect to a page showing the error message.
            String errorMessage = "Insufficient stock for product: " + product.getProductName();

            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/user/addToCartOutOfStock";
         }
      }
      

      userCart.setTotal(totalPrice);
      cartRepository.save(userCart);

      model.addAttribute("addresses", addressList);
      model.addAttribute("totalPrice", totalPrice);
      model.addAttribute("cartItems", cartItems);

      return "/checkOut/checkOut";
   }

//@ModelAttribute("userAddressDto")
//public UserAddressDto userAddressDto()
//{
//   return new UserAddressDto();
//}
//@PostMapping("addUserAddressInCheckOut")
//   public String useraddressformincheckout(@ModelAttribute("userAddressDto") UserAddressDto userAddressDto)
//{
//   userService.addUserAddressInCheckOut(userAddressDto);
//   return"redirect:/user/checkOut";
//}


   @GetMapping("/editUserAddressInCheckOut/{id}")
   public String editUserAddressInchekOut(@PathVariable("id") Long id, Model model)
   {
      UserAddress userAddress=userAddressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
      model.addAttribute("editAddressInCheckOut",userAddress);
      model.addAttribute("id",id);
      return "checkOut/checkOutEditUser";

   }
   @ModelAttribute("editAddressInCheckOut")
   public UserAddressDto userAddressDto()
   {
      return new UserAddressDto();
   }
   @PostMapping("/editUserAddressInCheckOut/{id}")
   public String editUserAddressInCheckOut(@PathVariable("id") long id,@ModelAttribute("editAddressInCheckOut") UserAddressDto userAddressDto)
   {
      userService.editUserAddressInCheckOut(id,userAddressDto);
      return "redirect:/user/checkOut";
   }
   @GetMapping("/deleteUserAddressInCheckOut/{id}")
   public String deleteuseraddress(@PathVariable("id") Long id)
   {
      this.userAddressRepository.deleteById(id);
      return "redirect:/user/profile";
   }

}
