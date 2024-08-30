package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.SellerDto;
import com.vonnueAmazonClone.amazonClone.DTO.SignInResponseDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Product;
import com.vonnueAmazonClone.amazonClone.Model.Seller;
import com.vonnueAmazonClone.amazonClone.Service.ProductService;
import com.vonnueAmazonClone.amazonClone.Service.SellerService;

import com.vonnueAmazonClone.amazonClone.Service.Validation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService sellerService;
    private final ProductService productService;
    private final Validation validation;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SellerController(SellerService sellerService, ProductService productService, Validation validation, BCryptPasswordEncoder passwordEncoder) {
        this.sellerService = sellerService;
        this.productService = productService;
        this.validation = validation;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint to save a new seller
    @PostMapping
    public ResponseEntity<?> createSeller(@RequestBody SellerDto sellerDto) {
        try{
            Seller savedSeller = sellerService.saveSeller(sellerDto);
            return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to login existing seller
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SellerDto sellerDto) {
        try {
            Seller seller = validation.findByEmail(sellerDto.getEmail());

            if (seller == null) {
                return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
            }

            if (passwordEncoder.matches(sellerDto.getPassword(), seller.getPassword())) {
                SignInResponseDto response=new SignInResponseDto("Sign-in successful", seller);
                return ResponseEntity.ok(response);
            } else {
                return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to update an existing seller
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeller(@PathVariable Long id, @RequestBody SellerDto sellerDto) {
        try{
            Seller updatedSeller = sellerService.updateSeller(id, sellerDto);
            return ResponseEntity.ok(updatedSeller);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to delete an existing seller
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeller(@PathVariable Long id) {
        try {
            sellerService.deleteSeller(id);
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

    @GetMapping("/all-sellers")
    public ResponseEntity<?> getAllUniqueSellers(
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Seller> sellers = sellerService.getAllSellers(page);
            return ResponseEntity.ok(sellers);
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