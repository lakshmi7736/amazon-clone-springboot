package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SubCategoryDto;
import com.vonnueAmazonClone.amazonClone.Model.Subcategory;

public interface SubCategoryService {
    SubCategoryDto saveSubCategory(SubCategoryDto subCategoryDto);
    SubCategoryDto updateSubCategory(Long id, SubCategoryDto subCategoryDto);
    void deleteSubCategory(Long id);
    Subcategory findBySubCategoryName(String name);
}
