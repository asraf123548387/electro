package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

}
