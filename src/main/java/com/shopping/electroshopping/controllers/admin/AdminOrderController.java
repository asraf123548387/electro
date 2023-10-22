package com.shopping.electroshopping.controllers.admin;

import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.repository.OrderItemsRepository;
import com.shopping.electroshopping.repository.OrderRepository;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.service.orderService.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequestMapping("/admin")
public class AdminOrderController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderServiceImpl orderService;
    @GetMapping("/orderList")
    public String orderList(Model model)
    {

        List<Order> orders=orderRepository.findAll();
        model.addAttribute("orderList",orders);

        return "order/orderList";
    }
    @GetMapping("/updateOrder/{id}")
    public String updateOrder(@PathVariable("id")Long orderId, Model model)
    {
        Order order=orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + orderId));
        model.addAttribute("order",order);
        model.addAttribute("orderId",orderId);
        return "/order/updateOrderStatus";
    }
    @PostMapping("/updateOrder/{id}")
    public String updateStatus(@PathVariable("id") Long orderId, @ModelAttribute("order") Order orderdto, RedirectAttributes redirectAttributes)
    {
       orderService.editstatus(orderId,orderdto);
       redirectAttributes.addFlashAttribute("hi","order status updated");
        return "redirect:/admin/orderList";
    }
}
