package com.shopping.electroshopping.controllers.admin;

import com.shopping.electroshopping.model.category.Category;
import com.shopping.electroshopping.model.categoryOffer.CategoryOffer;
import com.shopping.electroshopping.model.productOffer.ProductOffer;
import com.shopping.electroshopping.repository.CategoryOfferRepository;
import com.shopping.electroshopping.repository.CategoryRepository;
import com.shopping.electroshopping.repository.ProductOfferRepository;
import com.shopping.electroshopping.service.ProductOfferService.ProductOfferServiceImpl;
import com.shopping.electroshopping.service.categoryOfferService.CategoryOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCategoryOfferController {

@Autowired
    CategoryRepository categoryRepository;
@Autowired
CategoryOfferRepository categoryOfferRepository;
@Autowired
    ProductOfferServiceImpl productOfferService;

@Autowired
CategoryOfferService categoryOfferService;
//@GetMapping("/categoryOfferList")
//    public String showCategoryOfferList(Model model)
//{
//    List<CategoryOffer> categoryOfferList=categoryOfferRepository.findAll();
//    model.addAttribute("categoryOffer",categoryOfferList);
//    return "/categoryOffer/categoryOfferList";
//}


@ModelAttribute("categoryOffer")
public CategoryOffer categoryOffer()
{
    return new CategoryOffer();
}
@GetMapping("/addCategoryOffer")
    public String addCategoryOffer(Model model)
{
    List<Category> categoryList=categoryRepository.findAll();
    model.addAttribute("categoryList",categoryList);
    return "addCategoryOffer";
}
    @PostMapping("/addCategoryOffer")
    public String addCategoryForm(@ModelAttribute("categoryOffer")CategoryOffer categoryOffer)
    {
        categoryOfferService.addCategoryOffer(categoryOffer);
    return"redirect:/admin/productOfferList";
    }
    @GetMapping("/deleteCategoryOffer/{id}")
    public String deleteCategoryOffer(@PathVariable("id") long id)
    {
        categoryOfferRepository.deleteById(id);
        return "redirect:/admin/productOfferList";
    }


    @GetMapping("/updateCategoryOffer/{id}")
    public String updateCategoryOffer(@PathVariable("id") long id,Model model)
    {
        CategoryOffer categoryOffer=categoryOfferRepository.findById(id).orElseThrow(()->new IllegalArgumentException("in valid product Id: "+id));
        model.addAttribute("categoryOffer",categoryOffer);
        model.addAttribute("id",id);
        return "updateCategoryOffer";
    }
    @PostMapping("/updateCategoryOffer/{id}")
    public String updateProductOffers(@PathVariable("id") long id,@ModelAttribute("categoryOffer") CategoryOffer categoryOffer)
    {
        categoryOfferService.editCategoryOffer(id,categoryOffer);
        return "redirect:/admin/productOfferList";
    }










}
