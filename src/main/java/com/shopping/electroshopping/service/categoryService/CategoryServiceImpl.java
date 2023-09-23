package com.shopping.electroshopping.service.categoryService;

import com.shopping.electroshopping.dto.CategoryDto;
import com.shopping.electroshopping.model.category.Category;
import com.shopping.electroshopping.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service

public class CategoryServiceImpl implements  CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryServiceImpl() {
        this.categoryRepository = categoryRepository;
    }



    @Override
    public Category addcategory(CategoryDto categoryDto) {
//        Category category=new Category(categoryDto.getName());//add more geter inside the brace so wait
        Category category=new Category();
        category.setName(categoryDto.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) //deletion of category
    {
        this.categoryRepository.deleteById(id);

    }

    @Override
    @Transactional
    public void editCategoryByID(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category!=null)
        {
            category.setName(categoryDto.getName());
        }
       categoryRepository.save(category);


    }
public List<Category> getCategoryByName(String name)
{
    return categoryRepository.findByCategoryName(name);
}







}
