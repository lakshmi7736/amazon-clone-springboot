package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.CategoryRequestDto;
import com.vonnueAmazonClone.amazonClone.Model.CategoryRequest;

public interface CategoryRequestService {
    CategoryRequestDto createCategoryRequest(CategoryRequestDto categoryRequestDto);
    CategoryRequestDto approveCategoryRequest(Long requestId, CategoryRequestDto categoryRequestDto);
    CategoryRequestDto rejectCategoryRequest(Long requestId,CategoryRequestDto categoryRequestDto);
    CategoryRequest findByCategoryReqName(String name);
}
