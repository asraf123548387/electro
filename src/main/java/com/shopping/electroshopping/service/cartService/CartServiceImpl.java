package com.shopping.electroshopping.service.cartService;

import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.repository.CartItemsRepository;
import com.shopping.electroshopping.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Override
    public void deleteCartItemsByID(Long cartItemId) {
        this.cartItemsRepository.deleteById(cartItemId);
    }

//    public Cart save(Long userId) {
//
//        return cartRepository.save(userId);
//
//    }
}
