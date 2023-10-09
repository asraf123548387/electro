package com.shopping.electroshopping.controllers.userAdditionalFueture;

import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.service.referalOfferService.ReferralOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserReferalOfferController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ReferralOfferService referralOfferService;
    @GetMapping("/create")
    public String createReferralOffer(Model model) {
        // Implement logic to get the current user's ID (referrer) from the session

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Long referrerUserId = user.getId(); // You should implement this method

        model.addAttribute("referrerUserId", referrerUserId);
        return "create_referral_offer";
    }

    @PostMapping("/create")
    public String submitReferralOffer(
            @RequestParam("referrerUserId") Long referrerUserId,
            @RequestParam("referredUserId") Long referredUserId) {
        referralOfferService.createReferralOffer(referrerUserId, referredUserId);
        return "redirect:create?success=true";
    }

}
