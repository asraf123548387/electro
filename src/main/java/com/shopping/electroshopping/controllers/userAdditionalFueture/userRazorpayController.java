//package com.shopping.electroshopping.controllers.userAdditionalFueture;
//
//import com.razorpay.RazorpayException;
//import com.razorpay.Order;
//import com.shopping.electroshopping.service.paymentservice.RazorpayService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class RazorpayController {
//    @Autowired
//    private RazorpayService razorpayService;
//
//    @RequestMapping("/")
//    public String showPaymentForm() {
//        return "payment-form"; // This should match the name of your HTML template file
//    }
//
//    @PostMapping("/create-razorpay-order")
//    public String createRazorpayOrder(
//            @RequestParam Double amount,
//            @RequestParam String currency,
//            @RequestParam String description,
//            RedirectAttributes redirectAttributes,
//            Model model) {
//        try {
//            Order order = razorpayService.createOrder(amount, currency, description);
//            // Store the Razorpay order ID in a session or database for payment verification later
//            String razorpayOrderId = order.get("id");
//            redirectAttributes.addFlashAttribute("razorpayOrderId", razorpayOrderId);
//        } catch (RazorpayException e) {
//            // Handle exceptions here
//            e.printStackTrace();
//            model.addAttribute("error", "Failed to create Razorpay order.");
//            return "payment-form"; // Return to the payment form with an error message
//        }
//
//        return "redirect:/razorpay-payment"; // Redirect to a Razorpay payment page or controller
//    }
//
//    // Add more methods to handle Razorpay payment callbacks and verification
//}
