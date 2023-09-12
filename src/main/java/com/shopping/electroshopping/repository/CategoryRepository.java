package com.shopping.electroshopping.repository;

import com.shopping.electroshopping.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
