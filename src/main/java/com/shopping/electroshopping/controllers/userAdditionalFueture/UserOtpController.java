package com.shopping.electroshopping.controllers.user;


import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.service.userservice.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
;import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class UserOtpController {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/verify")
    public String showVerificationForm() {
        return "/otp/verification";
    }
    @PostMapping("/verify")
    public String processVerification(@RequestParam("otp") String enteredOTP, Principal principal, Model model) {
        String userEmail = principal.getName();
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(userEmail));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getOtp().equals(enteredOTP)) {
                user.setVerified(true);
                userRepository.save(user);
                // Redirect to a confirmation page or user dashboard
                return "redirect:/login";
            }
        }

        // OTP incorrect, show error message
        model.addAttribute("error", true);
        return "/otp/verification";
    }


    // this section is for forgot password

    @GetMapping("forgotPassword")
    public String forgotPassword()
    {
      return "/otp/forgotPassword";
    }
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, Model model)
    {
        User user = userService.getUserByEmail(email);
        String otp = generateOTP();
        user.setOtp(otp);

        // Save the updated user entity to the database
        userRepository.save(user);

        String emailss = user.getEmail();
        sendOTPEmail(emailss, otp);

      return "/otp/forgotPasswordVerify";


    }
    private void sendOTPEmail(String email, String otp) {
        if (email == null || email.isEmpty()) {
            // Handle the case where the email address is missing or empty.
            // You can log an error or throw an exception.
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for Signup");
        message.setText("Your OTP is: " + otp);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            // Handle email sending failure, e.g., log the error.
        }
    }


    private String generateOTP() {
        int otpLength = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
    @PostMapping("/resetPassword")
    public String processResetPassword(
            @RequestParam("otp") String otp,
            @RequestParam("newPassword") String newPassword,
            Model model
    ) {
        // Verify the OTP and reset the password
        boolean isOtpValid = userService.verifyOtpAndResetPassword(otp, newPassword);

        if (isOtpValid) {
            // Password reset was successful
            model.addAttribute("message", "Password reset successfully.");
            return "/login"; // Create a success page for password reset
        } else {
            // Invalid OTP or other error occurred
            model.addAttribute("error", "Invalid OTP. Please try again.");
            return "/otp/forgotPasswordVerify"; // Return to the verification page with an error message
        }
    }

}



