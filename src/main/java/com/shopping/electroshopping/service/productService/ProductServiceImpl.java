package com.shopping.electroshopping.service.productService;

import com.shopping.electroshopping.dto.ProductDto;
import com.shopping.electroshopping.model.category.Category;
import com.shopping.electroshopping.model.product.Product;
import com.shopping.electroshopping.repository.CategoryRepository;
import com.shopping.electroshopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
//            user  korch code for edit cheyan
        }
        productRepository.save(product);



    }
    public List<Product> getProductByName(String productName)
    {
        return productRepository.findByproductName(productName);
    }

}
