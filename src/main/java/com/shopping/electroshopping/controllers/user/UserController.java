package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.dto.ProductDto;
import com.shopping.electroshopping.dto.UserAddressDto;
import com.shopping.electroshopping.dto.UserSignUpDto;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.user.UserAddress;
import com.shopping.electroshopping.repository.UserAddressRepository;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.service.userservice.UserService;
import com.shopping.electroshopping.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    UserAddressRepository userAddressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/profile")
    public String profileView(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Long userId = user.getId();
        String usernamee=user.getUserName();
        String emailid=user.getEmail();
        String phone=user.getPhoneNumber();
        List<UserAddress> addresses = user.getAddresses();

        model.addAttribute("user_id",userId);
        model.addAttribute("username",usernamee);
        model.addAttribute("email",emailid);
        model.addAttribute("phone",phone);
        model.addAttribute("addresses",addresses);



        return "/user/userProfile";
    }

    @GetMapping("addUserAddress")
    public String addUserAddress(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Long userId = (user != null) ? user.getId() : null; // Check if user is not null

        model.addAttribute("user_id", userId);

        return "/user/addUserAddress";

    }
    @ModelAttribute("userAddressDto")
    public UserAddressDto userAddressDto()
    {
        return new UserAddressDto();
    }
    @PostMapping("addUserAddress")
    public String addUserAddressFormSubmit(@ModelAttribute("userAddressDto") UserAddressDto userAddressDto)
    {

      userService.addUserAddress(userAddressDto);
      return "redirect:/user/profile";
    }



    @GetMapping("addUserAddressInCheckOut")
    public String addUserAddressIncheckOut(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Long userId = (user != null) ? user.getId() : null; // Check if user is not null

        model.addAttribute("user_id", userId);

        return "/checkOut/checkOutAddUser";

    }
    @PostMapping("addUserAddressInCheckOut")
    public String addUserAddressFormSubmitINCheckOut(@ModelAttribute("userAddressDto") UserAddressDto userAddressDto)
    {

        userService.addUserAddress(userAddressDto);
        return "redirect:/user/checkOut";
    }







    @GetMapping("/updateProfile/{user_id}")
    public String editUserProfile(@PathVariable("user_id") Long user_id, Model model) {
        User user=userRepository.findById(user_id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + user_id));
        model.addAttribute("user", user);
        model.addAttribute("userId",user_id);



        return "user/updateProfile";
    }
    @ModelAttribute("user")
    public UserSignUpDto userSignUpDto()
    {
        return new UserSignUpDto();
    }
    @PostMapping ("/updateProfile/{id}")
    public String updateUser(@PathVariable("id") Long userId, @ModelAttribute("user") UserSignUpDto updateProfile)
    {
        userService.updateProfile(userId,updateProfile);
        return "redirect:/user/profile";
    }

}





