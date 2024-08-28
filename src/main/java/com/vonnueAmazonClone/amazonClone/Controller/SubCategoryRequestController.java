package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.SubCategoryRequestDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Subcategory;
import com.vonnueAmazonClone.amazonClone.Service.SubCategoryRequestService;
import com.vonnueAmazonClone.amazonClone.Service.SubCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategory-requests")
public class SubCategoryRequestController {
    private final SubCategoryRequestService subCategoryRequestService;

    private final SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryRequestController(SubCategoryRequestService subCategoryRequestService, SubCategoryService subCategoryService) {
        this.subCategoryRequestService = subCategoryRequestService;
        this.subCategoryService = subCategoryService;
    }
    // Endpoint to create a new sub category request
    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody SubCategoryRequestDto subCategoryRequestDto) {
        try {
            SubCategoryRequestDto savedRequest = subCategoryRequestService.createCategoryRequest(subCategoryRequestDto);
            return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get all  existing category
    @GetMapping("/all-sub-categories")
    public ResponseEntity<?> getAllSubCategories(
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Subcategory> subcategories = subCategoryService.getAllSubCategories(page);
            return ResponseEntity.ok(subcategories);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //to get SubcategoriesByCategory
    @GetMapping("/{categoryId}")
    public ResponseEntity<?>  getSubcategoriesByCategory(@PathVariable Long categoryId , @RequestParam(defaultValue = "0") int page) {
        try{
            List<Subcategory> subcategories=subCategoryService.getSubcategoriesByCategoryId(categoryId,page);
            return ResponseEntity.ok(subcategories);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

