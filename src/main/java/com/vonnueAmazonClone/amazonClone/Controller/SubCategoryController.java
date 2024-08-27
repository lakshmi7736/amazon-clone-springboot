package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.SubCategoryDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Subcategory;
import com.vonnueAmazonClone.amazonClone.Service.SubCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subCategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    // Endpoint to create a new category
    @PostMapping
    public ResponseEntity<?> createSubCategory(@RequestBody SubCategoryDto subCategoryDto) {
        try{
            SubCategoryDto savedSubCategory = subCategoryService.saveSubCategory(subCategoryDto);
            return new ResponseEntity<>(savedSubCategory, HttpStatus.CREATED);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // Endpoint to update an existing sub-category
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubCategory(@PathVariable Long id, @RequestBody SubCategoryDto subCategoryDto) {
        try {
            SubCategoryDto updatedSubCategory = subCategoryService.updateSubCategory(id, subCategoryDto);
            return ResponseEntity.ok(updatedSubCategory);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to delete an existing sub category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable Long id) {
        try {
            subCategoryService.deleteSubCategory(id);
            return ResponseEntity.ok().build();
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


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
