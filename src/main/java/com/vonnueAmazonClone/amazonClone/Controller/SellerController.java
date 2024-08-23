package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.SellerDto;
import com.vonnueAmazonClone.amazonClone.Model.Seller;
import com.vonnueAmazonClone.amazonClone.Service.SellerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    // Endpoint to save a new seller
    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody SellerDto sellerDto) {
        Seller savedSeller = sellerService.saveSeller(sellerDto);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    // Endpoint to update an existing seller
    @PutMapping("/{id}")
    public ResponseEntity<Seller> updateSeller(@PathVariable Long id, @RequestBody SellerDto sellerDto) {
        Seller updatedSeller = sellerService.updateSeller(id, sellerDto);
        return ResponseEntity.ok(updatedSeller);
    }
}