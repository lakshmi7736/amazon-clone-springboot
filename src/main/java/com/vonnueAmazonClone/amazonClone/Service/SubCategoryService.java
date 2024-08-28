package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SubCategoryDto;
import com.vonnueAmazonClone.amazonClone.Model.Subcategory;

import java.util.List;


public interface SubCategoryService {
    SubCategoryDto saveSubCategory(SubCategoryDto subCategoryDto);
    SubCategoryDto updateSubCategory(Long id, SubCategoryDto subCategoryDto);
    void deleteSubCategory(Long id);
    Subcategory findBySubCategoryName(String name);

    List<Subcategory> getAllSubCategories(int page);
    List<Subcategory> getSubcategoriesByCategoryId(Long categoryId, int page);
}
