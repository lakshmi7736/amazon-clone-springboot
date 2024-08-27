package com.vonnueAmazonClone.amazonClone.Controller;

import com.vonnueAmazonClone.amazonClone.DTO.ProductDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Product;
import com.vonnueAmazonClone.amazonClone.Service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //    create product
    @PostMapping(value="/add", consumes = "multipart/form-data")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto productDto,
                                           @RequestParam(value = "files", required = false) MultipartFile[] files) {
        List<byte[]> imageDataList = new ArrayList<>();
        productService.resizing(files);

        try {
            ProductDto savedProduct = productService.processAndSaveProduct(productDto, imageDataList);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //to get all products
    @GetMapping("/all-products")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Product> products = productService.getAllProducts(page);
            return ResponseEntity.ok(products);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //to get all products
    @GetMapping("/all-products-by-category/{id}")
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Product> products = productService.getAllProductsByCategory(id,page);
            return ResponseEntity.ok(products);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //to get all products
    @GetMapping("/all-products-by-subcategory/{id}")
    public ResponseEntity<?> getAllProductsBySubCategory(@PathVariable Long id,
                                                      @RequestParam(defaultValue = "0") int page) {
        try {
            List<Product> products = productService.getAllProductsBySubCategory(id,page);
            return ResponseEntity.ok(products);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    //to get products of prime
    @GetMapping("/prime-products")
    public ResponseEntity<?> getPrimeProducts(
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Product> products = productService.getPrimeProducts(page);
            return ResponseEntity.ok(products);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //to get products of prime
    @GetMapping("/madeForAmazon-products")
    public ResponseEntity<?> getAmazonMadeProducts(
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Product> products = productService.getMadeForAmazonProducts(page);
            return ResponseEntity.ok(products);
        }catch (InvalidDetailException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //to get products of cod available
    @GetMapping("/cod-products")
    public ResponseEntity<?> getCodProducts(
            @RequestParam(defaultValue = "0") int page) {
        try {
            List<Product> products = productService.getCodAvailable(page);
            return ResponseEntity.ok(products);
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
