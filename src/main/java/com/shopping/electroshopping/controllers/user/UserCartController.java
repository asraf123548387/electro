package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.model.cart.Cart;
import com.shopping.electroshopping.model.cart.CartItems;
import com.shopping.electroshopping.model.product.Product;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.repository.CartItemsRepository;
import com.shopping.electroshopping.repository.CartRepository;
import com.shopping.electroshopping.repository.ProductRepository;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.service.cartService.CartServiceImpl;
import com.shopping.electroshopping.service.categoryService.CategoryServiceImpl;
import com.shopping.electroshopping.service.productService.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserCartController {


    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartServiceImpl cartService;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemsRepository cartItemsRepository;



    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Long productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        Cart userCart = null;

        if (user != null) {
            // Retrieve or create user's cart
            List<Cart> userCarts = user.getCarts();

            if (userCarts == null || userCarts.isEmpty()) {
                // Create a new cart if the user doesn't have one
                userCart = new Cart();
                userCart.setUser(user);
            } else {
                // Use the first cart if the user already has one (you might want to handle multiple carts differently)
                userCart = userCarts.get(0);
            }

            // Create a CartItem
            Product product = productService.getProductById(productId);
            double discountedPrice =productService.calculateDiscountPrice(product);
            CartItems cartItem = new CartItems();
            cartItem.setProduct(product);
            cartItem.setQuantity(1); // Set the quantity as needed
            cartItem.setPrice(discountedPrice);// Set the price based on the product's price

            // Save the Cart entity before associating it with CartItems
            cartRepository.save(userCart);

            // Associate CartItem with Cart
            cartItem.setCart(userCart);

            // Save CartItem to CartItems table
            cartItemsRepository.save(cartItem);

            List<CartItems> cartItems = cartItemsRepository.findByCartUser(user);
            double totalPrice = cartItemsRepository.sumCartItemsPriceByUser(user);
            userCart.setTotal(totalPrice);
            double newtotal= userCart.getTotal();
            // Pass the cart items to the Thymeleaf template
            model.addAttribute("totalPrice", newtotal);
            model.addAttribute("cartItems", cartItems);
//            model.addAttribute("email",user);
            // Retrieve and add the product to the model
            model.addAttribute("product", product);

            return "cart";
        } else {
            // Handle the case where the user is null (e.g., redirect to a login page or display an error message).
            return "redirect:/login"; // Example: Redirect to the login page
        }
    }
    @GetMapping("/addToCartOutOfStock")
    public String addToCartoutofstock(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Cart cart=cartRepository.findByUser(user);
        List<CartItems> cartItems = cartItemsRepository.findByCartUser(user);
        double totalPrice = cartItemsRepository.sumCartItemsPriceByUser(user);
        cart.setTotal(totalPrice);
        double newtotal= cart.getTotal();
        // Pass the cart items to the Thymeleaf template
        model.addAttribute("totalPrice", newtotal);
        model.addAttribute("cartItems", cartItems);
        return "cart";

    }
    @GetMapping("/cart")
    public String showCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        List<CartItems> cartItems = cartItemsRepository.findByCartUser(user);
        double totalPrice = cartItemsRepository.sumCartItemsPriceByUser(user);
        // Pass the cart items to the Thymeleaf template
        model.addAttribute("totalPrice", totalPrice);
        // Pass the cart items to the Thymeleaf template
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @GetMapping("/deleteCartItems/{id}")
    public String deleteCartItem(@PathVariable("id") Long cartItemId) {
       this.cartService.deleteCartItemsByID(cartItemId);
       return "redirect:/user/cart";
    }

    @PostMapping("/updateCartItemQuantity")
    public String updateCartItemQuantity(@RequestParam Long cartItemId, @RequestParam int quantity, Model model) {
        cartService.updateCartItemQuantity(cartItemId, quantity);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
//        double totalPrice = cartItemsRepository.sumCartItemsPriceByUserSecond(user);
        // Pass the cart items to the Thymeleaf template

//        model.addAttribute("totalPric", totalPrice);
        return "redirect:/user/cart";
    }









}
