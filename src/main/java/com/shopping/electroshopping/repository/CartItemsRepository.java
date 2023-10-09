package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.cart.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
    List<CartItems> findByCartUser(User user);

    List<CartItems> findByCartUser(com.shopping.electroshopping.model.user.User user);
    @Query("SELECT COALESCE(SUM(ci.price * ci.quantity), 0.0) FROM CartItems ci WHERE ci.cart.user = :user")
     double sumCartItemsPriceByUser(com.shopping.electroshopping.model.user.User user);

//    double sumCartItemsPriceByUserSecond(@Param("user") User user);

    List<CartItems> findByUser(com.shopping.electroshopping.model.user.User user);
//    @Query("SELECT COALESCE(SUM(ci.price * ci.quantity), 0.0) FROM CartItems ci WHERE ci.cart.user = :user")
//    double sumCartItemsPriceByUserSecond(@Param("user") com.shopping.electroshopping.model.user.User user);
}
