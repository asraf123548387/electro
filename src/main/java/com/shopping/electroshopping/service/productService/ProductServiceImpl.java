package com.shopping.electroshopping.service.productService;

import com.shopping.electroshopping.dto.ProductDto;
import com.shopping.electroshopping.model.category.Category;
import com.shopping.electroshopping.model.product.Product;
import com.shopping.electroshopping.repository.CategoryRepository;
import com.shopping.electroshopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;



    @Override
    public Product addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageName(productDto.getImageName());
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        product.setCategory(category);

        return productRepository.save(product);
    }


    @Override
    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void editproduct(Long id, ProductDto productDto) {
        Product product=productRepository.findById(id).orElse(null);
        if(product!=null)
        {
         product.setImageName(productDto.getImageName());
         product.setProductName(productDto.getProductName());
         product.setPrice(productDto.getPrice());
         product.setCategory(product.getCategory());
         product.setDescription(productDto.getDescription());
        }
        productRepository.save(product);



    }
    public List<Product> getProductByName(String productName)
    {
        return productRepository.findByproductName(productName);
    }

    public Product getProductById(Long productId) {

        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            return productOptional.get(); // Return the product if it exists
        } else {
            return null;
        }
    }

    }

