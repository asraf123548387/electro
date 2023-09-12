package com.shopping.electroshopping.service.categoryService;

import com.shopping.electroshopping.dto.CategoryDto;
import com.shopping.electroshopping.model.category.Category;

public interface CategoryService {

   Category addcategory(CategoryDto categoryDto);
   public void deleteCategoryById(Long id);
   public void editCategoryByID(Long id,CategoryDto categoryDto);
}
