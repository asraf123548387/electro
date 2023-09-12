package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.dto.UserSignUpDto;
import com.shopping.electroshopping.service.userservice.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserSignUpController {
private UserService userService;

    public UserSignUpController(UserService userService) {
        super();
        this.userService = userService;
    }
    @ModelAttribute("user")
    public UserSignUpDto userSignUpDto()
    {
        return new UserSignUpDto();
    }



    @GetMapping()
    public String showsignUp()
    {
        return "signUp";
    }
    @PostMapping
    public String registeruser(@ModelAttribute("user") UserSignUpDto signUpDto)
    {
        userService.save(signUpDto);
        return "redirect:/registration?success";
    }

}
