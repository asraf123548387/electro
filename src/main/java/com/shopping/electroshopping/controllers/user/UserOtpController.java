package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.service.otpservice.OTPServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserOtpController {
    @Autowired
    private OTPServive otpServive;


    @PostMapping("/regist")
    public String registerUser(@RequestParam("phoneNumber") String phoneNumber) {
        // Validate and save user information
        // Send OTP

        otpServive.sendOTP(phoneNumber);
        return "redirect:/login";

//        return ResponseEntity.ok("OTP sent successfully.");
    }


    @PostMapping("/verif")
    public ResponseEntity<String> verifyOTP(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("otp") String otp) {
        if (otpServive.verifyOTP(phoneNumber, otp)) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }
    }
    @GetMapping("/otpSignUp")
    public String otpSignUP()
    {
        return "otpSignUp";
    }

}

