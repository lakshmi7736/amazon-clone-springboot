package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.CategoryRequestDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Category;
import com.vonnueAmazonClone.amazonClone.Model.CategoryRequest;
import com.vonnueAmazonClone.amazonClone.Repository.CategoryRepository;
import com.vonnueAmazonClone.amazonClone.Repository.CategoryRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryRequestServiceImpl implements CategoryRequestService {
    private final CategoryRequestRepository categoryRequestRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryRequestServiceImpl(CategoryRequestRepository categoryRequestRepository, CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryRequestRepository = categoryRequestRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryRequestDto createCategoryRequest(CategoryRequestDto categoryRequestDto) {
        CategoryRequest byName = categoryRequestRepository.findByName(categoryRequestDto.getSuggestedCategoryName());
        if (categoryService.findByCategoryName(categoryRequestDto.getSuggestedCategoryName()) != null) {
            throw new InvalidDetailException("category is already exist.");
        }
        if(byName!=null){
            throw new InvalidDetailException("request already made with status :"+ byName.getStatus());
        }
        CategoryRequest request = new CategoryRequest();
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("PENDING");
        request.setSuggestedCategoryName(categoryRequestDto.getSuggestedCategoryName());
        request.setCreatedBy(categoryRequestDto.getCreatedBy());
        request = categoryRequestRepository.save(request);
        categoryRequestDto.setRequestId(request.getRequestId());
        categoryRequestDto.setStatus(request.getStatus());
        categoryRequestDto.setCreatedAt(request.getCreatedAt());
        return categoryRequestDto;
    }
    @Override
    public CategoryRequestDto approveCategoryRequest(Long requestId, CategoryRequestDto categoryRequestDto) {
        if (categoryService.findByCategoryName(categoryRequestDto.getSuggestedCategoryName()) != null) {
            throw new InvalidDetailException("category is already exist.");
        }
//        CategoryRequest byName = categoryRequestRepository.findByName(categoryRequestDto.getSuggestedCategoryName());
//        if(byName!=null && !byName.getStatus().equals("PENDING")){
//            throw new InvalidDetailException("request already made with status :"+ byName.getStatus());
//        }
        return categoryRequestRepository.findById(requestId).map(existingRequest->{
            existingRequest.setStatus("APPROVED");
            Category category= new Category();
            category.setName(existingRequest.getSuggestedCategoryName());
            categoryRepository.save(category);
            categoryRequestRepository.save(existingRequest);
            categoryRequestDto.setStatus("APPROVED");
            return categoryRequestDto;
        }).orElseThrow(() -> new EntityNotFoundException("Category request not found with id: " + requestId));
    }

    @Override
    public CategoryRequestDto rejectCategoryRequest(Long requestId,CategoryRequestDto categoryRequestDto) {
        if (categoryService.findByCategoryName(categoryRequestDto.getSuggestedCategoryName()) != null) {
            throw new InvalidDetailException("category is already exist.");
        }
        CategoryRequest byName = categoryRequestRepository.findByName(categoryRequestDto.getSuggestedCategoryName());
        if(byName!=null && !byName.getStatus().equals("PENDING")){
            throw new InvalidDetailException("request already made with status :"+ byName.getStatus());
        }
        return categoryRequestRepository.findById(requestId).map(existingRequest -> {
            existingRequest.setStatus("REJECTED");
            categoryRequestRepository.save(existingRequest);
            categoryRequestDto.setStatus("REJECTED");
            return categoryRequestDto;
        }).orElseThrow(() -> new InvalidDetailException("Category request not found."));
    }

}



