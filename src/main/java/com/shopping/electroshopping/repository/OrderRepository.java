package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);
    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double getTotalOrderAmount();
    List<Order> findByUserEmail(String email);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.orderDate = :todayDate")
    Double getTotalOrderAmountForToday(@Param("todayDate") LocalDate todayDate);
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderMonth = 10")
    Double getTotalOrderAmountForOctober();
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.orderMonth = 11")
    Double getTotalOrderAmountForNovember();
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> findByOrderDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
