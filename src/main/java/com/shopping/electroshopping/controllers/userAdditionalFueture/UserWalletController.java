package com.shopping.electroshopping.controllers.userAdditionalFueture;

import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.wallet.Wallet;
import com.shopping.electroshopping.repository.CartRepository;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.repository.WalletRepository;
import com.shopping.electroshopping.service.wallet.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserWalletController {
    @Autowired
    WalletServiceImpl walletService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    WalletRepository walletRepository;
    @GetMapping("wallet")
    public String walletshow(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Cart userCart = cartRepository.findByUser(user);
        double newtotal=userCart.getTotal();
        model.addAttribute("total",newtotal);

        double walletBalance = walletService.getCurrentWalletBalance();
        model.addAttribute("walletBalance", walletBalance);
        return "/wallet/wallet";

    }
    @GetMapping("useWallet")
    public String useWallet(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Cart userCart = cartRepository.findByUser(user);
        double newtotal=userCart.getTotal();
        Wallet wallet=user.getWallets();
        wallet.withdraw(newtotal);
       walletRepository.save(wallet);


        return "redirect:/user/orderPlaced";


    }
}
