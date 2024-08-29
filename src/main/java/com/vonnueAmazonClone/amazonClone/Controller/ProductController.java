package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.ProductDto;
import com.vonnueAmazonClone.amazonClone.Handle.ImageProcessingException;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Product;
import com.vonnueAmazonClone.amazonClone.Service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.vonnueAmazonClone.amazonClone.Service.imageProcessing.deserializeImageBlob;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //    create product

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto productDto,
                                           @RequestParam(value = "files", required = false) MultipartFile[] files) {
        try {
            // Check if seller is null
            if (productDto.getSeller() == null) {
                return new ResponseEntity<>("Seller information is required", HttpStatus.BAD_REQUEST);
            }
            if(files == null) {
                return new ResponseEntity<>("Seller information is required", HttpStatus.BAD_REQUEST);
            }

            List<byte[]> imageDataList = new ArrayList<>();
            for(MultipartFile file : files){
                if (!file.isEmpty()) {
                    try {
                        productService.resizeAndProcessImages(imageDataList, file);
                    } catch (ImageProcessingException e) {
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                    }
                }
            }
            try{
                ProductDto savedProduct = productService.processAndSaveProduct(productDto, imageDataList);
                return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
            }catch (IOException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //to get products based on filters
    @GetMapping
    public ResponseEntity<?> getProductsByCriteria(
            @RequestParam(defaultValue = "0") int averageRating,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String seller,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long subCategoryId,
            @RequestParam(required = false) Long nestedSubCategoryId,
            @RequestParam(required = false) Boolean prime,
            @RequestParam(required = false) Boolean cod,
            @RequestParam(required = false) Boolean madeForAmazon,
            @RequestParam(defaultValue = "0") BigDecimal minPrice, // Added
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Product> products = productService.getProductsByCriteria(nestedSubCategoryId,averageRating, seller, brand, categoryId, subCategoryId, page, prime, cod, madeForAmazon, minPrice, maxPrice);
            List<ProductDto> productDTOs = new ArrayList<>();
            for (Product product : products) {
                if (product.getImageBlob() != null) {
                    try {
                        List<byte[]> imageDataList = deserializeImageBlob(product.getImageBlob());
                        if (!imageDataList.isEmpty()) {
                            String encodedImage = Base64.getEncoder().encodeToString(imageDataList.get(0));
                            productDTOs.add(new ProductDto(product, encodedImage));
                        } else {
                            // Handling empty image data list
                            productDTOs.add(new ProductDto(product, null));
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        // Handling errors in image deserialization
                        return ResponseEntity.badRequest().body("Error deserializing image for product ID: " + product.getId());
                    }
                } else {
                    // Handling null image blob
                    productDTOs.add(new ProductDto(product, null));
                }
            }
            return ResponseEntity.ok(productDTOs);
        } catch (InvalidDetailException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
