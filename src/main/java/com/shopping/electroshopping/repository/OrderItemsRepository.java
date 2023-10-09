package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.order.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems,Long> {

}
