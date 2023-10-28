package com.shopping.electroshopping.controllers.admin;

//import com.shopping.electroshopping.dto.ProductOfferDTO;
import com.shopping.electroshopping.model.categoryOffer.CategoryOffer;
import com.shopping.electroshopping.model.product.Product;
import com.shopping.electroshopping.model.productOffer.ProductOffer;
import com.shopping.electroshopping.repository.CategoryOfferRepository;
import com.shopping.electroshopping.repository.ProductOfferRepository;
import com.shopping.electroshopping.repository.ProductRepository;
import com.shopping.electroshopping.service.ProductOfferService.ProductOfferService;
import com.shopping.electroshopping.service.ProductOfferService.ProductOfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProductOfferController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductOfferServiceImpl productOfferService;
    @Autowired
    ProductOfferRepository productOfferRepository;
    @Autowired
    CategoryOfferRepository categoryOfferRepository;

    @GetMapping("/productOfferList")
    public String showProductOfferList(Model model)
    {
        List<CategoryOffer> categoryOfferList=categoryOfferRepository.findAll();
        List<ProductOffer> productOffers=productOfferRepository.findAll();
        model.addAttribute("productOffer",productOffers);

        model.addAttribute("categoryOffer",categoryOfferList);
        return"productOfferList";
    }
    @ModelAttribute("productOffer")
    public ProductOffer productOffer()
    {
        return new ProductOffer();
    }

    @GetMapping("/addProductOffer")
    public String addProductOffer(Model model)
    {
        List<Product> productList=productRepository.findAll();
        model.addAttribute("productList",productRepository.findAll());
        return"addProductOffer";
    }
    @PostMapping("/addProductOffer")
    public String addProductForm(@ModelAttribute("productOffer")ProductOffer productOffer)
    {

        productOfferService.addProductOffer(productOffer);
        return"redirect:/admin/productOfferList";
    }

    @GetMapping("/updateProductOffer/{productOfferId}")
    public String updateProductOffer(@PathVariable("productOfferId") Long productOfferId,Model model)
    {
        ProductOffer productOffer=productOfferRepository.findById(productOfferId).orElseThrow(()->new IllegalArgumentException("in valid product Id: "+productOfferId));
        model.addAttribute("productOffer",productOffer);
        model.addAttribute("productOfferId",productOfferId);
        return "updateProductOffer";
    }
    @PostMapping("/updateProductOffer/{id}")
    public String updateProductOffer(@PathVariable("id") Long id,@ModelAttribute("productOffer") ProductOffer productOffer)
    {
        productOfferService.editProductOffer(id,productOffer);
        return "redirect:/admin/productOfferList";
    }
    @GetMapping("/deleteProductOffer/{productOfferId}")
    public String deleteProductOffer(@PathVariable("productOfferId")Long productOfferId)
    {
        this.productOfferRepository.deleteById(productOfferId);
        return "redirect:/admin/productOfferList";
    }


}
