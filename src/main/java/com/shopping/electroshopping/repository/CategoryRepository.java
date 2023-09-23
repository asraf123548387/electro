package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query
            ("SELECT u FROM Category u WHERE u.name LIKE :name%")
    List<Category> findByCategoryName(String name);
}
