package com.shopping.electroshopping.service.categoryOfferService;

import com.shopping.electroshopping.model.category.Category;
import com.shopping.electroshopping.model.categoryOffer.CategoryOffer;
import com.shopping.electroshopping.repository.CategoryOfferRepository;
import com.shopping.electroshopping.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryOfferService {
@Autowired
    CategoryOfferRepository categoryOfferRepository;
@Autowired
    CategoryRepository categoryRepository;

    public  void editCategoryOffer(long id, CategoryOffer categoryOffer) {
        CategoryOffer categoryOffer1= categoryOfferRepository.findById(id).orElse(null);
        if(categoryOffer1!=null)
        {
            categoryOffer1.setExpirationDate(categoryOffer.getExpirationDate());
            categoryOffer1.setDiscountAmount(categoryOffer.getDiscountAmount());
            categoryOffer1.setCreatedDate(categoryOffer.getCreatedDate());
        }

        categoryOfferRepository.save(categoryOffer1);

    }

    public CategoryOffer addCategoryOffer(CategoryOffer categoryOffer) {
        categoryOffer.setDiscountAmount(categoryOffer.getDiscountAmount());
        categoryOffer.setCreatedDate(categoryOffer.getCreatedDate());
        categoryOffer.setExpirationDate(categoryOffer.getExpirationDate());
        if (categoryOffer.getCategory() == null) {
            throw new IllegalArgumentException("category is required.");
        }
        Category category=categoryRepository.findById(categoryOffer.getCategory().getId()).orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + categoryOffer.getCategory().getId()));
        categoryOffer.setCategory(category);
        return categoryOfferRepository.save(categoryOffer);
    }
}
