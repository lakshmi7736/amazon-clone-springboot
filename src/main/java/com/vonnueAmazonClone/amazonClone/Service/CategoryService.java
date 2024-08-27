package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.CategoryDto;
import com.vonnueAmazonClone.amazonClone.Model.Category;

import java.util.List;


public interface CategoryService {
     CategoryDto saveCategory(CategoryDto categoryDto);
     CategoryDto updateCategory(Long id, CategoryDto categoryDto);
     void deleteCategory(Long id);
      Category findByCategoryName(String name);

      //to find all categories
      List<Category> getAllCategories(int page);

}
