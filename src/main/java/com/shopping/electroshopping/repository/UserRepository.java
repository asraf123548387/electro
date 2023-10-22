package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email LIKE :email%")
    List<User> findByName(String email);
    User findByPhoneNumber(String phoneNumber);


    User findByUserName(String username);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);


    @Query("SELECT u FROM User u WHERE u.otp = :otp")
    User findByOtp(@Param("otp") String otp);
    @Query("SELECT COUNT(u) FROM User u")
    long countUsers();

    long countByIsBlocked(boolean b);
}
