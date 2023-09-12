package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email LIKE :email%")
    List<User> findByName(String email);
    User findByPhoneNumber(String phoneNumber);



}
