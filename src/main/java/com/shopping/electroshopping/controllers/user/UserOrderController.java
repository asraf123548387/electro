package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.model.cart.CartItems;
import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.model.order.OrderItems;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.repository.*;
import com.shopping.electroshopping.service.orderService.OrderServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserOrderController {
@Autowired
    OrderServiceImpl orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartItemsRepository cartItemsRepository;
    @Autowired
    CartRepository cartRepository;
@GetMapping("/orderPlaced")
    public String orderplaced(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    User user = userRepository.findByEmail(email);
    double totalPrice = cartItemsRepository.sumCartItemsPriceByUser(user);
    Cart userCart = cartRepository.findByUser(user);
   double newtotal=userCart.getTotal();


    LocalDateTime currentDateTime = LocalDateTime.now();

    Order userOrder = new Order();

    userOrder.setUser(user);
    userOrder.setPaymentMethod("COD");
    userOrder.setStatus("confirmed");
    userOrder.setOrderDate(currentDateTime);
    userOrder.setTotalAmount((int) totalPrice);
    Order savedOrder = orderRepository.save(userOrder);
//    List<CartItems> cartItems = cartItemsRepository.findByUser(user);
    OrderItems orderItems=new OrderItems();
     orderItems.setOrder(savedOrder);
     orderItems.setQuantity(1);
     orderItems.setUnitPrice(totalPrice);
    List<Order> orders = orderRepository.findByUser(user);
    orderItemsRepository.save(orderItems);
    model.addAttribute("totalPrice",newtotal);
//    model.addAttribute("orderid",orders);


    return "/order/plcedOrder";


}


@GetMapping("/showOrder")
    public String showOrders(Model model)
{
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    // Fetch the list of orders for the user (assuming a method findByUserEmail in your repository)
    List<Order> orderList = orderRepository.findByUserEmail(email);
    model.addAttribute("orderList", orderList);
    return "/order/showOrder";

}
@GetMapping("cancelOrder/{id}")
    public String cancelOrder(@PathVariable("id")Long id)
{
    this.orderService.deleteProductByid(id);
    return "redirect:/user/showOrder";
}

}