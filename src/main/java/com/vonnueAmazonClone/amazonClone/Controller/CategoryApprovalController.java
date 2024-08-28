package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.CategoryRequestDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Service.CategoryRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category-requests")
public class CategoryApprovalController {

    private final CategoryRequestService categoryRequestService;

    @Autowired
    public CategoryApprovalController(CategoryRequestService categoryRequestService) {
        this.categoryRequestService = categoryRequestService;
    }

    // Endpoint to approve an existing category request
    @PatchMapping("/approve/{requestId}")
    public ResponseEntity<?> approveRequest(@PathVariable Long requestId, @RequestBody CategoryRequestDto categoryRequest  ) {
        try{
            CategoryRequestDto approvedRequest = categoryRequestService.approveCategoryRequest(requestId,categoryRequest);
            return new ResponseEntity<>(approvedRequest, HttpStatus.OK);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to reject an existing category request
    @PatchMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectRequest(@PathVariable Long requestId,@RequestBody CategoryRequestDto categoryRequestDto) {
        try{
            CategoryRequestDto rejectedRequest = categoryRequestService.rejectCategoryRequest(requestId, categoryRequestDto);
            return new ResponseEntity<>(rejectedRequest, HttpStatus.OK);
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
