package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.dto.UserAddressDto;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.user.UserAddress;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserCheckOutController {
   @Autowired
   UserRepository userRepository;
   @Autowired
   UserServiceImpl userService;

   @GetMapping("/checkOut")
   public String checkOut(Model model) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String email = authentication.getName();
      User user = userRepository.findByEmail(email);

      List<UserAddress> addressList = user.getAddresses();
      model.addAttribute("addresses", addressList);



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






}
