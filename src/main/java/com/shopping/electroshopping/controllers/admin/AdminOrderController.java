package com.shopping.electroshopping.controllers.admin1;

import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.repository.CartItemsRepository;
import com.shopping.electroshopping.repository.CartRepository;
import com.shopping.electroshopping.repository.OrderItemsRepository;
import com.shopping.electroshopping.repository.OrderRepository;
import com.shopping.electroshopping.service.orderService.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
public class AdminOrderController {
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
}
