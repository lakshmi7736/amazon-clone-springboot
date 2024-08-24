package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SubCategoryDto;

public interface SubCategoryService {
    SubCategoryDto saveSubCategory(SubCategoryDto subCategoryDto);
    SubCategoryDto updateSubCategory(Long id, SubCategoryDto subCategoryDto);
    void deleteSubCategory(Long id);
}
