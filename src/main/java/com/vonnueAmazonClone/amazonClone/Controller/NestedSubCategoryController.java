package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.NestedSubCategoryDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.NestedSubCategory;
import com.vonnueAmazonClone.amazonClone.Service.NestedSubCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nestedSubCategories")
public class NestedSubCategoryController {

    private final NestedSubCategoryService nestedSubCategoryService;

    public NestedSubCategoryController(NestedSubCategoryService nestedSubCategoryService) {
        this.nestedSubCategoryService = nestedSubCategoryService;
    }


    // Endpoint to create a new nested-sub-category
    @PostMapping
    public ResponseEntity<?> createNestedSubCategory(@RequestBody NestedSubCategoryDto nestedSubCategoryDto) {
        try{
            NestedSubCategoryDto saveNestedSubCategory = nestedSubCategoryService.saveNestedSubCategory(nestedSubCategoryDto);
            return new ResponseEntity<>(saveNestedSubCategory, HttpStatus.CREATED);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // Endpoint to update an existing nested-sub-category
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNestedSubCategory(@PathVariable Long id, @RequestBody NestedSubCategoryDto nestedSubCategoryDto) {
        try {
            NestedSubCategoryDto updateNestedSubCategory = nestedSubCategoryService.updateNestedSubCategory(id, nestedSubCategoryDto);
            return ResponseEntity.ok(updateNestedSubCategory);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to delete an existing nested-sub-category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNestedSubCategory(@PathVariable Long id) {
        try {
            nestedSubCategoryService.deleteNestedSubCategory(id);
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
