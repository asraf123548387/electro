package com.shopping.electroshopping.controllers.admin;

import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.service.orderService.OrderServiceImpl;
import com.shopping.electroshopping.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private UserRepository customerRepository;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private UserServiceImpl userService;
//    @GetMapping("/")
//    public String home() {
//        return "index";
//    }
    @GetMapping("/home")
    public String adminhome(Model model) {
      double totalAmount= orderService.getTotalOrderAmount();
      double totalAmountToday= orderService.getTotalOrderAmountForToday();
      double totalAmountForOctober = orderService.getTotalOrderAmountForOctober();
      double totalForNovember = orderService.getTotalOrderAmountForNovember();
      long totalNumberOfUser=userService.getTotalNumberOfUsers();
        long blockedUserCount = customerRepository.countByIsBlocked(true);
       long unblockedUserCount=customerRepository.countByIsBlocked(false);

        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        data.put("january", 30);
        data.put("february", 50);
        data.put("march", 70);
        data.put("April", 90);
        data.put("May", 25);
        data.put("June", 0);
        data.put("July", 0);
        data.put("August", 0);
        data.put("September", 0);
        data.put("October", (int) totalAmountForOctober);
        data.put("November", (int) totalForNovember);
        data.put("December",0);
        model.addAttribute("keySet", data.keySet());
        model.addAttribute("values", data.values());
        model.addAttribute("unblockedUser", unblockedUserCount);
        model.addAttribute("blockedUser", blockedUserCount);
        model.addAttribute("amount",totalAmount);
        model.addAttribute("todayAmount",totalAmountToday);
        model.addAttribute("user",totalNumberOfUser);

        return "adminhome";
    }




    @GetMapping("/listUsers")
    public String listUsers(Model model, @RequestParam(defaultValue = "0") int page)
    {
        int pageSize = 10; // Number of items per page
        Page<User> usersPage = customerRepository.findAll(PageRequest.of(page, pageSize));
        model.addAttribute("listUsers", usersPage);
        return "listusers";
    }


    @GetMapping("/search")
    public String searchuser(@RequestParam("email")String email,Model model)
    {
        List<User> list=userService.getCustomerByName(email);
         model.addAttribute("listUsers",list);
        return "listusers";
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
