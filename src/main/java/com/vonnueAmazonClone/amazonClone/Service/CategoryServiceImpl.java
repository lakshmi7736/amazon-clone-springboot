package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.CategoryDto;
import com.vonnueAmazonClone.amazonClone.Model.Category;
import com.vonnueAmazonClone.amazonClone.Repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Base64;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        if (categoryDto.getImage() != null && !categoryDto.getImage().isEmpty()) {
            // Directly set the decoded image on the entity
            category.setImage(Base64.getDecoder().decode(categoryDto.getImage()));
        }
        category.setSeller(categoryDto.getSeller()); // Ensure the seller is managed and exists in the DB
        category = categoryRepository.save(category);
        categoryDto.setId(category.getId()); // Set the ID on the DTO from the saved entity
        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        return categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(categoryDto.getName());
            if (categoryDto.getImage() != null && !categoryDto.getImage().isEmpty()) {
                // Directly set the decoded image on the entity
                existingCategory.setImage(Base64.getDecoder().decode(categoryDto.getImage()));
            }
            existingCategory.setSeller(categoryDto.getSeller()); // Handle seller updates appropriately
            categoryRepository.save(existingCategory);
            return categoryDto;
        }).orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }
}
