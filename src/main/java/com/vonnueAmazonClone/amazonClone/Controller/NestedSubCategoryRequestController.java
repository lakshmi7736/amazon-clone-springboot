package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.NestedSubCategory;
import com.vonnueAmazonClone.amazonClone.Service.NestedSubCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nested-subcategory-requests")
public class NestedSubCategoryRequestController {

    private final NestedSubCategoryService nestedSubCategoryService;

    @Autowired
    public NestedSubCategoryRequestController(NestedSubCategoryService nestedSubCategoryService) {
        this.nestedSubCategoryService = nestedSubCategoryService;
    }

    // Endpoint to get all  existing category
    @GetMapping("/all-nested-sub-categories")
    public ResponseEntity<?> getAllNestedSubCategories(
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<NestedSubCategory> categories = nestedSubCategoryService.getAllNestedSubCategories(page);
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

    //to get NestedSubcategories By SubCategory
    @GetMapping("/{subCategoryId}")
    public ResponseEntity<?>  getNestedSubcategoriesBySubCategory(@PathVariable Long subCategoryId , @RequestParam(defaultValue = "0") int page) {
        try{
            List<NestedSubCategory> nestedSubCategories=nestedSubCategoryService.getNestedSubcategoriesBySubCategoryId(subCategoryId,page);
            return ResponseEntity.ok(nestedSubCategories);
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
