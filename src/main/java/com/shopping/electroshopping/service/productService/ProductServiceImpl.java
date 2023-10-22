package com.shopping.electroshopping.service.productService;

import com.shopping.electroshopping.dto.ProductDto;
import com.shopping.electroshopping.model.category.Category;
import com.shopping.electroshopping.model.categoryOffer.CategoryOffer;
import com.shopping.electroshopping.model.product.Product;
import com.shopping.electroshopping.model.productOffer.ProductOffer;
import com.shopping.electroshopping.repository.CategoryRepository;
import com.shopping.electroshopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        product.setStock(productDto.getStock());
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
         product.setStock(productDto.getStock());
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

    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

//    public double calculateDiscountPrice(Product product) {
//        {
//            double originalPrice = product.getPrice();
//           Optional<Category> category=categoryRepository.findById(product.getCategory().getId());
//
//
//            List<ProductOffer> offers = product.getOffers();
//            if (offers != null && !offers.isEmpty()) {
//
//                double discountAmount = offers.get(0).getDiscountAmount();
//
//                // Calculate the discounted price
//                double discountedPrice = originalPrice - (originalPrice * (discountAmount / 100.0));
//
//                // Round the discounted price to 2 decimal places (adjust as needed)
//                return Math.round(discountedPrice * 100.0) / 100.0;
//            }
//
//
//
//            // If there are no offers, return the original price
//            return originalPrice;
//        }
//    }

    public double calculateDiscountPrice(Product product) {
        double originalPrice = product.getPrice();
        double categoryDiscount = 0.0; // Default category discount
        double productDiscount = 0.0; // Default product discount

        // Retrieve the category of the product
        Category productCategory = product.getCategory();

        if (productCategory != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(productCategory.getId());

            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();

                List<CategoryOffer> categoryOffers = category.getOffers();

                if (categoryOffers != null && !categoryOffers.isEmpty()) {
                    // Assuming the first category offer is applied
                    categoryDiscount = categoryOffers.get(0).getDiscountAmount();
                }
            }
        }

        List<ProductOffer> productOffers = product.getOffers();
        if (productOffers != null && !productOffers.isEmpty()) {
            // Assuming the first product offer is applied
            productDiscount = productOffers.get(0).getDiscountAmount();

        }

        // Calculate the total discount by combining category and product discounts
        double totalDiscount = Math.max(categoryDiscount, productDiscount);
        if (totalDiscount == 0.0) {
            // If there are no category or product offers, return the original price
            return originalPrice;
        }
        // Calculate the discounted price
        double discountedPrice = originalPrice - (originalPrice * (totalDiscount / 100.0));

        // Round the discounted price to 2 decimal places (adjust as needed)
        return Math.round(discountedPrice * 100.0) / 100.0;
    }


}

