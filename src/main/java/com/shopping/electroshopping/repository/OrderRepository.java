package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);

    List<Order> findByUserEmail(String email);
}
