package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.CategoryDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Category;
import com.vonnueAmazonClone.amazonClone.Repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        if(findByCategoryName(categoryDto.getName())!=null){
            throw new InvalidDetailException("category is already exist.");
        }
        Category category = new Category();
        category.setName(categoryDto.getName());
        category = categoryRepository.save(category);
        categoryDto.setId(category.getId()); // Set the ID on the DTO from the saved entity
        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {

        return categoryRepository.findById(id).map(existingCategory -> {
            if(categoryDto.getName()!=null && !categoryDto.getName().equals(existingCategory.getName())){
                if(findByCategoryName(categoryDto.getName())!=null){
                    throw new InvalidDetailException("category is already taken.");
                }
            }
            existingCategory.setName(categoryDto.getName());
            categoryRepository.save(existingCategory);
            return categoryDto;
        }).orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        categoryRepository.deleteById(id);
    }


    @Override
    public Category findByCategoryName(String name) {

        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories(int page) {
        int size = 6;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Category> pageResult = categoryRepository.findAll(pageRequest);
        if (pageResult.hasContent()) {
            return pageResult.getContent(); // Return the list of products
        }else {
            throw new InvalidDetailException("No items to display");
        }
    }



}
