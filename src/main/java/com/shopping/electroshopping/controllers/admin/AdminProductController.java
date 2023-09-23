package com.shopping.electroshopping.controllers.admin;

import com.shopping.electroshopping.dto.ProductDto;
import com.shopping.electroshopping.model.product.Product;
import com.shopping.electroshopping.repository.CategoryRepository;
import com.shopping.electroshopping.repository.ProductRepository;
import com.shopping.electroshopping.service.productService.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminProductController {
    private ProductServiceImpl productService;

    public AdminProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/productList")
    public String ProductList(Model model)

    {

     List<Product> products=productRepository.findAll();
     model.addAttribute("listproduct",products);

        return "/product/listProducts";
    }
    @ModelAttribute("product")
    public ProductDto productDto()
    {
        return new ProductDto();
    }
    @GetMapping("/addProducts")
    public String addproductsForm(Model model)
    {
        model.addAttribute("categories",categoryRepository.findAll());
        return "/product/addProducts";
    }
    @PostMapping("/addProducts")
    public String addProductstoDatabase(@ModelAttribute("product")ProductDto productDto )
    {


        productService.addProduct(productDto);
        return "redirect:/admin/productList";
    }


    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") long id)
    {
        this.productService.deleteProductById(id);
        return "redirect:/admin/productList";
    }
    @GetMapping("/updateProduct/{id}")
    public String updateProductForm(@PathVariable(value = "id")long productId, Model model)
    {
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException("in valid product Id: "+productId));
        model.addAttribute("categories",categoryRepository.findAll());
        model.addAttribute("product",product);
        model.addAttribute("pro",productId);
        model.addAttribute("categories",categoryRepository.findAll());

        return "/product/updateProduct";
    }
    @PostMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable("id") Long productId,@ModelAttribute("product") ProductDto productDto)
    {
        productService.editproduct(productId,productDto);
        return "redirect:/admin/productList";
    }
    @GetMapping("/productSearch")
    public String searchUser(@RequestParam("productName")String productName,Model model)
    {
        List<Product> list=productService.getProductByName(productName);
        model.addAttribute("listproduct",list);
        return "/product/listProducts";
    }






}
