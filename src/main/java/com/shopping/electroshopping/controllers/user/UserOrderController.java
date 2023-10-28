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

import java.time.LocalDate;
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
    int orderMonth=currentDateTime.getMonthValue();
    int orderYear=currentDateTime.getYear();


    Order userOrder = new Order();

    userOrder.setUser(user);
    userOrder.setPaymentMethod("COD");
    userOrder.setStatus("confirmed");
    userOrder.setOrderDate(LocalDate.from(currentDateTime));
    userOrder.setOrderMonth(orderMonth);
    userOrder.setOrderYear(orderYear);
    userOrder.setTotalAmount((int) totalPrice);
    Order savedOrder = orderRepository.save(userOrder);

    for (CartItems cartItem : userCart.getCartItems()) {
        OrderItems orderItem = new OrderItems();
        orderItem.setOrder(savedOrder);
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setUnitPrice(cartItem.getPrice());

        // You need to set the product or product ID here, based on your data model
         orderItem.setProduct(cartItem.getProduct());

        orderItemsRepository.save(orderItem);
    }

//    List<Order> orders = orderRepository.findByUser(user);
//    orderItemsRepository.save(orderItems);
// You can delete the cart items if needed
 cartItemsRepository.deleteAll(userCart.getCartItems());

// Or mark them as ordered, depending on your business logic
// userCart.getCartItems().forEach(cartItem -> cartItem.setOrdered(true));
// cartRepository.save(userCart);


    model.addAttribute("totalPrice",newtotal);
//    model.addAttribute("orderid",orders);


    return "plcedOrder";


}

    @GetMapping("/orderPlacedWithWallet")
    public String orderplacedwithwallet(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        double totalPrice = cartItemsRepository.sumCartItemsPriceByUser(user);
        Cart userCart = cartRepository.findByUser(user);
        double newtotal=userCart.getTotal();


        LocalDateTime currentDateTime = LocalDateTime.now();
        int orderMonth=currentDateTime.getMonthValue();
        int orderYear=currentDateTime.getYear();

        Order userOrder = new Order();

        userOrder.setUser(user);
        userOrder.setPaymentMethod("Wallet");
        userOrder.setStatus("confirmed");
        userOrder.setOrderDate(LocalDate.from(currentDateTime));
        userOrder.setOrderMonth(orderMonth);
        userOrder.setOrderMonth(orderYear);
        userOrder.setTotalAmount((int) totalPrice);
        Order savedOrder = orderRepository.save(userOrder);

        for (CartItems cartItem : userCart.getCartItems()) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(savedOrder);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getPrice());

            // You need to set the product or product ID here, based on your data model
            orderItem.setProduct(cartItem.getProduct());

            orderItemsRepository.save(orderItem);
        }

//    List<Order> orders = orderRepository.findByUser(user);
//    orderItemsRepository.save(orderItems);
// You can delete the cart items if needed
        cartItemsRepository.deleteAll(userCart.getCartItems());

// Or mark them as ordered, depending on your business logic
// userCart.getCartItems().forEach(cartItem -> cartItem.setOrdered(true));
// cartRepository.save(userCart);


        model.addAttribute("totalPrice",newtotal);
//    model.addAttribute("orderid",orders);


        return "plcedOrder";


    }
@GetMapping("/showOrder")
    public String showOrders(Model model)
{
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    User user = userRepository.findByEmail(email);
    // Fetch the list of orders for the user (assuming a method findByUserEmail in your repository)
    List<Order> orderList = orderRepository.findByUserEmail(email);
    Cart userCart = cartRepository.findByUser(user);
    double newtotal=userCart.getTotal();
    model.addAttribute("total",newtotal);
    model.addAttribute("orderList", orderList);
    return "showOrder";

}
@GetMapping("cancelOrder/{id}")
    public String cancelOrder(@PathVariable("id")Long id)
{
//    this.orderService.deleteProductByid(id);
    orderService.cancelOrder(id);
    return "redirect:/user/showOrder";
}
    @GetMapping("cancellOrder/{id}")
    public String cancellOrder(@PathVariable("id")Long id) {
        this.orderService.deleteProductByid(id);
        return "redirect:/user/showOrder";
    }

}


