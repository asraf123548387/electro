package com.shopping.electroshopping.controllers.userAdditionalFueture;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.shopping.electroshopping.dto.OrderDtoPaypal;
import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.model.cart.CartItems;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.repository.CartItemsRepository;
import com.shopping.electroshopping.repository.CartRepository;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.service.paymentservice.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserPaypalController {
    @Autowired
  PaypalService paypalService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemsRepository cartItemsRepository;

    public static final String SUCCESS_URL="pay/success";
    public static final String CANCEL_URL="pay/cancel";

  @GetMapping("/user/home")
    public String home(Model model){
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Cart userCart = cartRepository.findByUser(user);
      List<CartItems> cartItems = cartItemsRepository.findByCartUser(user);

        if (userCart != null) {
            double total = userCart.getTotal();
            model.addAttribute("amount", total);
            model.addAttribute("cartItems", cartItems);
        }
            return "/payment/payment";

    }
@PostMapping("/pay")
    public String payment(@ModelAttribute("order") OrderDtoPaypal orderDtoPaypal) throws PayPalRESTException {
      orderDtoPaypal.setIntend("sale");
     Payment payment= paypalService.createPayment(orderDtoPaypal.getPrice(), orderDtoPaypal.getCurrency(), orderDtoPaypal.getMethod(), orderDtoPaypal.getIntend(), orderDtoPaypal.getDescription(),"http://localhost:8080/"+CANCEL_URL,"http://localhost:8080/"+SUCCESS_URL);
for(Links link:payment.getLinks())
{
    if(link.getRel().equals("approval_url"))
    {
        return "redirect:"+link.getHref();

    }
}
return "redirect:/home";
    }
    @GetMapping(value = CANCEL_URL)
    public String cancelPay(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("not","payment not successful");
        return "redirect:/user/checkOut";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/home";
    }

}
