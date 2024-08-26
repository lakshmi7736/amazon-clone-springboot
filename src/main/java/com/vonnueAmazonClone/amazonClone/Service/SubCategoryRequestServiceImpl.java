package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SubCategoryRequestDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.SubCategoryRequest;
import com.vonnueAmazonClone.amazonClone.Model.Subcategory;
import com.vonnueAmazonClone.amazonClone.Repository.SubCategoryRepository;
import com.vonnueAmazonClone.amazonClone.Repository.SubCategoryRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubCategoryRequestServiceImpl implements SubCategoryRequestService{

    public final SubCategoryRequestRepository subCategoryRequestRepository;
    public final SubCategoryRepository subCategoryRepository;
    private final SubCategoryService subCategoryService;
    @Autowired
    public SubCategoryRequestServiceImpl(SubCategoryRequestRepository subCategoryRequestRepository, SubCategoryRepository subCategoryRepository, SubCategoryService subCategoryService) {
        this.subCategoryRequestRepository = subCategoryRequestRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryService = subCategoryService;
    }

    @Override
    public SubCategoryRequestDto createCategoryRequest(SubCategoryRequestDto subCategoryRequestDto) {
        if(subCategoryService.findBySubCategoryName(subCategoryRequestDto.getSuggestedSubCategoryName())!=null){
            throw new InvalidDetailException("sub category is already exist.");
        }
        if(subCategoryRequestRepository.findByName(subCategoryRequestDto.getSuggestedSubCategoryName())!=null){
            throw new InvalidDetailException("request already made.");
        }
        SubCategoryRequest request = new SubCategoryRequest();
        request.setCreatedAt(LocalDateTime.now());
        request.setCreatedBy(subCategoryRequestDto.getCreatedBy());
        request.setStatus("PENDING");
        request.setCategory(subCategoryRequestDto.getCategory());
        request.setSuggestedSubCategoryName(subCategoryRequestDto.getSuggestedSubCategoryName());
        request=subCategoryRequestRepository.save(request);
        subCategoryRequestDto.setCategory(request.getCategory());
        subCategoryRequestDto.setRequestId(request.getRequestId());
        subCategoryRequestDto.setCreatedAt(request.getCreatedAt());
        subCategoryRequestDto.setStatus(request.getStatus());
        subCategoryRequestDto.setSuggestedSubCategoryName(request.getSuggestedSubCategoryName());
        return subCategoryRequestDto;
    }

    @Override
    public SubCategoryRequestDto approveCategoryRequest(Long requestId, SubCategoryRequestDto subCategoryRequestDto ) {
        if(subCategoryService.findBySubCategoryName(subCategoryRequestDto.getSuggestedSubCategoryName())!=null){
            throw new InvalidDetailException("sub category is already exist.");
        }
//        SubCategoryRequest data=subCategoryRequestRepository.findByName(subCategoryRequestDto.getSuggestedSubCategoryName());
//        if(data!=null && !data.getStatus().equals("PENDING")){
//            throw new InvalidDetailException("request already made with status :"+ data.getStatus());
//        }
        return subCategoryRequestRepository.findById(requestId).map(existingRequest->{
            existingRequest.setStatus("APPROVE");
            Subcategory subcategory= new Subcategory();
            subcategory.setSubCategoryName(existingRequest.getSuggestedSubCategoryName());
            subcategory.setCategoryId(existingRequest.getCategory());
            subCategoryRepository.save(subcategory);
            subCategoryRequestRepository.save(existingRequest);
            subCategoryRequestDto.setStatus("APPROVE");
            return subCategoryRequestDto;
        }).orElseThrow(() -> new EntityNotFoundException("Category request not found with id: " + requestId));
    }



    @Override
    public SubCategoryRequestDto rejectCategoryRequest(Long requestId, SubCategoryRequestDto subCategoryRequestDto) {
        if(subCategoryService.findBySubCategoryName(subCategoryRequestDto.getSuggestedSubCategoryName())!=null){
            throw new InvalidDetailException("sub category is already exist.");
        }
        SubCategoryRequest data=subCategoryRequestRepository.findByName(subCategoryRequestDto.getSuggestedSubCategoryName());
        if(data!=null && !data.getStatus().equals("PENDING")){
            throw new InvalidDetailException("request already made with status :"+ data.getStatus());
        }
        return subCategoryRequestRepository.findById(requestId).map(exsitingRequest->{
            exsitingRequest.setStatus("REJECTED");
            subCategoryRequestRepository.save(exsitingRequest);
            subCategoryRequestDto.setStatus("REJECTED");
            return subCategoryRequestDto;
        }).orElseThrow(() -> new InvalidDetailException("Category request not found."));
    }

    @Override
    public SubCategoryRequest findBySubCategoryReqName(String name) {
        return subCategoryRequestRepository.findByName(name);
    }
}
