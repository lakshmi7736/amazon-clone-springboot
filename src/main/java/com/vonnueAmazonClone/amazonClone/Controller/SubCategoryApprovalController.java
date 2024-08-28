package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.SubCategoryRequestDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Service.SubCategoryRequestService;
import com.vonnueAmazonClone.amazonClone.Service.SubCategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/subcategory-requests")
public class SubCategoryApprovalController {

    private final SubCategoryService subCategoryService;

    private final SubCategoryRequestService subCategoryRequestService;

    @Autowired
    public SubCategoryApprovalController(SubCategoryService subCategoryService, SubCategoryRequestService subCategoryRequestService) {
        this.subCategoryService = subCategoryService;
        this.subCategoryRequestService = subCategoryRequestService;
    }

    // Endpoint to approve an existing sub category request
    @PatchMapping("/approve/{requestId}")
    public ResponseEntity<?> approveRequest(@PathVariable Long requestId, @RequestBody SubCategoryRequestDto subCategoryRequestDto  ) {
        try{
            SubCategoryRequestDto approvedRequest = subCategoryRequestService.approveCategoryRequest(requestId,subCategoryRequestDto);
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
    public ResponseEntity<?> rejectRequest(@PathVariable Long requestId,@RequestBody SubCategoryRequestDto subCategoryRequestDto) {
        try{
            SubCategoryRequestDto rejectedRequest = subCategoryRequestService.rejectCategoryRequest(requestId, subCategoryRequestDto);
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
