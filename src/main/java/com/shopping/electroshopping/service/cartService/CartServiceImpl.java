package com.shopping.electroshopping.service.cartService;

import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.model.cart.CartItems;
import com.shopping.electroshopping.repository.CartItemsRepository;
import com.shopping.electroshopping.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        Optional<CartItems> optionalCartItem = cartItemsRepository.findById(cartItemId);
        if (optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();

            // Update the quantity of the cart item
            cartItem.setQuantity(quantity);

            // Save the updated cart item back to the database
            cartItemsRepository.save(cartItem);
        }  // Handle the case where the cart item with the given ID doesn't exist

    }

//    public Cart save(Long userId) {
//
//        return cartRepository.save(userId);
//
//    }
}
