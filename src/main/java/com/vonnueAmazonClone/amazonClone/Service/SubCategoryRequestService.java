package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SubCategoryRequestDto;
import com.vonnueAmazonClone.amazonClone.Model.SubCategoryRequest;

public interface SubCategoryRequestService {
    SubCategoryRequestDto createCategoryRequest(SubCategoryRequestDto subCategoryRequestDto);
    SubCategoryRequestDto approveCategoryRequest(Long requestId, SubCategoryRequestDto subCategoryRequestDto );
    SubCategoryRequestDto rejectCategoryRequest(Long requestId,SubCategoryRequestDto subCategoryRequestDto);
}
