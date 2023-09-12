package com.shopping.electroshopping.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
    @GetMapping("/")
    public String home()
    {
        return "index";
    }

    @GetMapping("/checkout")
    public String check()
    {
        return "checkout";
    }
    @GetMapping("/cart")
    public String cart()
    {
        return "cart";
    }



}
