package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.model.category.Category;
import com.shopping.electroshopping.model.categoryOffer.CategoryOffer;
import com.shopping.electroshopping.model.product.Product;
import com.shopping.electroshopping.model.productOffer.ProductOffer;
import com.shopping.electroshopping.repository.CategoryRepository;
import com.shopping.electroshopping.repository.ProductRepository;
import com.shopping.electroshopping.service.productService.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/user")
@Controller
public class userProductController {
    @Autowired
    ProductServiceImpl productService;
@Autowired
    ProductRepository productRepository;
@Autowired
    CategoryRepository categoryRepository;
    @GetMapping("/productView")
    public String viewProductDetail(@RequestParam("productId") Long productId, Model model) {
        // Retrieve the product details based on productId
        Product product = productService.getProductById(productId);
        double discountedPrice = productService.calculateDiscountPrice(product);

        List<Product> productList=productRepository.findAll();

        model.addAttribute("listProducts",productList);
        // Add the product to the model for use in the view
        model.addAttribute("product", product);
        model.addAttribute("discountPrice",discountedPrice);

        // Return the product detail view template
        return "/product/productDetailView";
    }



    @GetMapping("/DELLCollection/{id}")
    public String dellCollection(@PathVariable Long id, Model model)
    {
        Category hpCategory = categoryRepository.findById(id).orElse(null);

        if (hpCategory != null) {
            List<Product> productList = productRepository.findByCategory(hpCategory);
            model.addAttribute("listProducts", productList);
        }
//        List<Product> productList=productRepository.findAll();
        List<Category> categories=categoryRepository.findAll();
//        model.addAttribute("listProducts",productList);
        model.addAttribute("categoryList",categories);
        return "/product/dellCollection";
    }
    @GetMapping("/SAMSUNGCollection/{id}")
    public String samsungCollection(@PathVariable Long id, Model model)
    {
        Category hpCategory = categoryRepository.findById(id).orElse(null);

        if (hpCategory != null) {
            List<Product> productList = productRepository.findByCategory(hpCategory);
            model.addAttribute("listProducts", productList);
        }
        double categoryDiscount = 0.0;
        if (hpCategory.getOffers() != null) {
            // Get the current date (or the date you want to use for comparison)
            LocalDate currentDate = LocalDate.now();

            for (CategoryOffer offer : hpCategory.getOffers()) {
                // Check if the offer's expiration date is after or equal to the current date
                LocalDate expirationDate = LocalDate.parse(offer.getExpirationDate());

                if (!currentDate.isAfter(expirationDate)) {
                    categoryDiscount = offer.getDiscountAmount();
                    break; // Exit the loop once a valid offer is found
                }
            }
        }
        List<Category> categories=categoryRepository.findAll();
        model.addAttribute("categoryList",categories);
        model.addAttribute("categoryDiscount", categoryDiscount);
        return "/product/samsungCollection";
    }
    @GetMapping("/HPCollection/{id}")
    public String hpCollection(@PathVariable Long id, Model model)
    {
        Category hpCategory = categoryRepository.findById(id).orElse(null);

        if (hpCategory != null) {
            List<Product> productList = productRepository.findByCategory(hpCategory);
            model.addAttribute("listProducts", productList);
        }
//        List<Product> productList=productRepository.findAll();
        List<Category> categories=categoryRepository.findAll();
//        model.addAttribute("listProducts",productList);
        model.addAttribute("categoryList",categories);
        return "/product/hpCollection";
    }

    @GetMapping("/productSearch")
    public String searchProduct(@RequestParam("productName")String productName,Model model)
    {
        List<Product> list=productService.getProductByName(productName);
        model.addAttribute("listProducts",list);
        return "index";
    }

}
