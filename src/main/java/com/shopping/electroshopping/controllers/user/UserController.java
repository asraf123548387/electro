package com.shopping.electroshopping.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {
    @GetMapping("/productView") // just for tempeporary
    public String productView() {
        return "/product/productDetailView";
    }
}





