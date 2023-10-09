package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.dto.UserSignUpDto;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Controller
@RequestMapping("/registration")

public class UserSignUpController {
    @Autowired
private UserService userService;
    @Autowired
    private JavaMailSender javaMailSender;
    public UserSignUpController(UserService userService) {
        super();
        this.userService = userService;
    }
    @ModelAttribute("user")
    public UserSignUpDto userSignUpDto()
    {
        return new UserSignUpDto();
    }



    @GetMapping()
    public String showsignUp(Model model)
    {
        model.addAttribute("user", new UserSignUpDto());
        return "signUp";
    }
    @PostMapping
    public String registeruser(@ModelAttribute("user") UserSignUpDto signUpDto,Model model)
    {
        if (userService.isEmailAlreadyRegistered(signUpDto.getEmail())) {

            // Email address is already registered, display an error message
            model.addAttribute("error", "This email address is already registered. Please use a different email address.");
            return "signUp"; // Return to the registration form with an error message
        }
        if (signUpDto.getPhoneNumber() == null || signUpDto.getPhoneNumber().length() != 10) {
            String errorMessage = "Phone number must have exactly 10 digits.";
            model.addAttribute("error1", errorMessage);
            return "signUp";
        }

        String otp = generateOTP();
        signUpDto.setOtp(otp);
//        user.setOtp(otp);
        sendOTPEmail(signUpDto.getEmail(), otp);
        userService.save(signUpDto);
//        userService.saveUser(user);

        return "redirect:user/verify";
    }


   //new code for study to implement the otp


    // Implement the method to send OTP via email
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


    // Generate OTP method (similar to what you've mentioned before)
    private String generateOTP() {
        int otpLength = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
