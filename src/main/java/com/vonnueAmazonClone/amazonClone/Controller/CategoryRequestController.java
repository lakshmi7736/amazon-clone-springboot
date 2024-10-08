package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.CategoryRequestDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Category;
import com.vonnueAmazonClone.amazonClone.Service.CategoryRequestService;
import com.vonnueAmazonClone.amazonClone.Service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-requests")
public class CategoryRequestController {
    private final CategoryRequestService categoryRequestService;
    private final CategoryService categoryService;

    @Autowired
    public CategoryRequestController(CategoryRequestService categoryRequestService, CategoryService categoryService) {
        this.categoryRequestService = categoryRequestService;
        this.categoryService = categoryService;
    }

    // Endpoint to create a new category request
    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody CategoryRequestDto categoryRequest) {
        try {
            CategoryRequestDto savedRequest = categoryRequestService.createCategoryRequest(categoryRequest);
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
    @GetMapping("/all-categories")
    public ResponseEntity<?> getAllCategories(
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Category> categories = categoryService.getAllCategories(page);
            return ResponseEntity.ok(categories);
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