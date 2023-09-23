package com.shopping.electroshopping.controllers.admin;

import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private UserRepository customerRepository;
    @Autowired
    private UserServiceImpl userService;
//    @GetMapping("/")
//    public String home() {
//        return "index";
//    }
    @GetMapping("/home")
    public String adminhome() {
        return "adminhome";
    }
    @GetMapping("/listUsers")
    public String listusers(Model model)
    {
        List<User> customers=customerRepository.findAll();
        model.addAttribute("listUsers",customers);
        return "user/listusers";
    }


    @GetMapping("/search")
    public String searchuser(@RequestParam("email")String email,Model model)
    {
        List<User> list=userService.getCustomerByName(email);
         model.addAttribute("listUsers",list);
        return "user/listusers";
    }
    @GetMapping ("blockUser/{id}")
    public String blockUser(@PathVariable Long id)
    {
   userService.blockUser(id);
   return "redirect:/admin/listUsers";

    }

    @GetMapping("unblockUser/{id}")
    public String unblockUser(@PathVariable Long id)
    {
        userService.unblockUser(id);

        return "redirect:/admin/listUsers";
    }




}
