package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.NestedSubCategoryDto;
import com.vonnueAmazonClone.amazonClone.Model.NestedSubCategory;
import com.vonnueAmazonClone.amazonClone.Model.Subcategory;

import java.util.List;

public interface NestedSubCategoryService {

    NestedSubCategoryDto saveNestedSubCategory(NestedSubCategoryDto nestedSubCategoryDto);
    NestedSubCategory findByNestedSubCategoryName(String name);

    NestedSubCategoryDto updateNestedSubCategory(Long id, NestedSubCategoryDto nestedSubCategoryDto);
    void deleteNestedSubCategory(Long id);

    List<NestedSubCategory> getAllNestedSubCategories(int page);

    List<NestedSubCategory> getNestedSubcategoriesBySubCategoryId(Long subCategoryId, int page);
}
