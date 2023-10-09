package com.shopping.electroshopping.controllers.userAdditionalFueture;

import com.shopping.electroshopping.model.product.Product;
import com.shopping.electroshopping.model.user.User;
import com.shopping.electroshopping.model.wishlist.WishListItem;
import com.shopping.electroshopping.repository.ProductRepository;
import com.shopping.electroshopping.repository.UserRepository;
import com.shopping.electroshopping.repository.WishListRepository;
import com.shopping.electroshopping.service.productService.ProductServiceImpl;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserWishlistController {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductServiceImpl productService;
    @GetMapping("/addToWishList")
    public String addToWishList(@RequestParam("productId") Long productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Product product = productService.getProductById(productId);


         WishListItem  wishListItem=new WishListItem();
         wishListItem.setUser(user);
         wishListItem.setProduct(product);
         wishListRepository.save(wishListItem);
         return "index";

  }
    @GetMapping("/seeWishList")
    public String SeeWishlist(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        List<WishListItem> wishListItems=wishListRepository.findByWishListItemUser(user);
        model.addAttribute("wishListItem",wishListItems);
        return "wishlist/wishlist";
    }
    @GetMapping("/deleteWishList/{id}")
    public String deleteWishList(@PathVariable("id") Long id)
    {
        this.wishListRepository.deleteById(id);
        return "redirect:/user/seeWishList";

    }


}
