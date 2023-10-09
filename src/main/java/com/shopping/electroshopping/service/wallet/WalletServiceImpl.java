package com.shopping.electroshopping.service.wallet;

import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.wallet.Wallet;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public double getCurrentWalletBalance() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Assuming a user has a reference to their wallet
            Wallet wallet = user.getWallets();

            if (wallet != null) {
                // Get the wallet balance
                return wallet.getBalance();
            }
        }

        // Default to a balance of 0 if the user or wallet is not found
        return 0.0;
    }
    }


