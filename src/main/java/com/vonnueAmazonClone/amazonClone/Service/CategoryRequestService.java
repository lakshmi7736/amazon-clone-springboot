package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.CategoryRequestDto;

public interface CategoryRequestService {
    CategoryRequestDto createCategoryRequest(CategoryRequestDto categoryRequestDto);
    CategoryRequestDto approveCategoryRequest(Long requestId, CategoryRequestDto categoryRequestDto);
    CategoryRequestDto rejectCategoryRequest(Long requestId,CategoryRequestDto categoryRequestDto);
}
