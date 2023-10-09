package com.shopping.electroshopping.controllers.user;

import com.shopping.electroshopping.model.category.Category;
import com.shopping.electroshopping.model.product.Product;
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

        // Add the product to the model for use in the view
        model.addAttribute("product", product);

        // Return the product detail view template
        return "/product/productDetailView";
    }



    @GetMapping("/dellCollection")
    public String dellCollection(Model model)
    {
        Category dellCategory = categoryRepository.findByName("DELL");

        if (dellCategory != null) {
            List<Product> productList = productRepository.findByCategory(dellCategory);
            model.addAttribute("listProducts", productList);
        }
//        List<Product> productList=productRepository.findAll();
        List<Category> categories=categoryRepository.findAll();
//        model.addAttribute("listProducts",productList);
        model.addAttribute("categoryList",categories);
        return "/product/dellCollection";
    }
    @GetMapping("/samsungCollection")
    public String samsungCollection(Model model)
    {
        Category samsungCategory = categoryRepository.findByName("SAMSUNG");

        if (samsungCategory != null) {
            List<Product> productList = productRepository.findByCategory(samsungCategory);
            model.addAttribute("listProducts", productList);
        }
//        List<Product> productList=productRepository.findAll();
        List<Category> categories=categoryRepository.findAll();
//        model.addAttribute("listProducts",productList);
        model.addAttribute("categoryList",categories);
        return "/product/samsungCollection";
    }
    @GetMapping("/hpCollection")
    public String hpCollection(Model model)
    {
        Category hpCategory = categoryRepository.findByName("HP");

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
