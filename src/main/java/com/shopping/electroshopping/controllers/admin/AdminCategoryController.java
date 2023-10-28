package com.shopping.electroshopping.controllers.admin;

import com.shopping.electroshopping.dto.CategoryDto;
import com.shopping.electroshopping.dto.ProductDto;
import com.shopping.electroshopping.model.category.Category;
import com.shopping.electroshopping.repository.CategoryRepository;
import com.shopping.electroshopping.service.categoryService.CategoryServiceImpl;
import com.shopping.electroshopping.service.productService.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
   private CategoryServiceImpl categoryService;


    public AdminCategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categoryList")
    public String getProductListPage(Model model)
    {
        List<Category> categories=categoryRepository.findAll();
        model.addAttribute("listcategory",categories);
        return "listCategory";
    }
    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable(value = "id") long id)
    {
        this.categoryService.deleteCategoryById(id);
        return "redirect:/admin/categoryList";
    }
    @GetMapping("/updateCategory/{id}")
    public String updatetheCategoryForm(@PathVariable(value = "id")long CategoryId,Model model)
    {
        Category category=categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + CategoryId));
        model.addAttribute("category",category);
        model.addAttribute("categoryId",CategoryId);
        return "updateCategory";
    }

    @PostMapping("/updateCategory/{id}")
    public String updateCategory(@PathVariable("id") Long categoryId ,@ModelAttribute("category") CategoryDto categoryDto)
    {
        categoryService.editCategoryByID(categoryId,categoryDto);
        return "redirect:/admin/categoryList";
    }


    @ModelAttribute("category")
    public CategoryDto categoryDto()
    {
        return new CategoryDto();
    }

    @GetMapping("/addCategory")
    public String showAddCategoryPage()
    {
        return "addCategory";
    }
    @PostMapping("/addCategoryForm")
    public String addingCategoryIntoDatabase(@ModelAttribute("category")CategoryDto categoryDto)
    {
     categoryService.addcategory(categoryDto);
     return "redirect:/admin/categoryList";
    }
    @GetMapping("/categorySearch")
    public String searchCategory(@RequestParam ("name")String name, Model model)
    {
        List<Category> list=categoryService.getCategoryByName(name);
        model.addAttribute("listcategory",list);
        return "listCategory";
    }


}
