package com.shopping.electroshopping.controllers.userAdditionalFueture;

import com.razorpay.RazorpayException;
import com.razorpay.Order;
import com.shopping.electroshopping.service.paymentservice.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class userRazorpayController {
    @Autowired
    private RazorpayService razorpayService;

 @GetMapping("/razorPay")
    public String showPaymentForm() {

     return "redirect:" + "https://rzp.io/l/Ypz4fpK"; // This should match the name of your HTML template file
    }

    @PostMapping("/create-razorpay-order")
    public String createRazorpayOrder(
            @RequestParam Double amount,
            @RequestParam String currency,
            @RequestParam String description,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session) {
        try {
            // Create a Razorpay order
            Order order = razorpayService.createOrder(amount, currency, description);

            // Store the Razorpay order ID in the session for payment verification later
            String razorpayOrderId = order.get("id");
            session.setAttribute("razorpayOrderId", razorpayOrderId);

            // Redirect to the Razorpay payment page
            return "redirect:" + "https://rzp.io/l/Ypz4fpK";
        } catch (RazorpayException e) {
            // Handle exceptions here
            e.printStackTrace();
            model.addAttribute("error", "Failed to create Razorpay order.");
            return "paymentForm"; // Return to the payment form with an error message
        }
    }
    // Add more methods to handle Razorpay payment callbacks and verification
}
