package com.shopping.electroshopping.service.otpservice;
import java.util.Random;

import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPServive {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TwilioSMSProvider twilioSMSProvider;
    private String generateRandomOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    public void sendOTP(String phoneNumber)
    {
        String otpCode=generateRandomOTP();
        User user=userRepository.findByPhoneNumber(phoneNumber);
        user.setOtp(otpCode);
        userRepository.save(user);
        twilioSMSProvider.sendSMS(phoneNumber," hi iam ashraf :your otp is:"+otpCode);
    }
    public boolean verifyOTP(String phoneNumber, String enteredOTP) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user != null && user.getOtp() != null && user.getOtp().equals(enteredOTP)) {
            // Clear the OTP after successful verification
            user.setOtp(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }


}



