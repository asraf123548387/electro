package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.user.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository  extends JpaRepository<UserAddress,Long> {
}
