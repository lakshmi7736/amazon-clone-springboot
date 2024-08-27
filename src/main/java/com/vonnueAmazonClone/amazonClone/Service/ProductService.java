package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.ProductDto;
import com.vonnueAmazonClone.amazonClone.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {
    void resizing(MultipartFile[] files) ;
    ProductDto processAndSaveProduct(ProductDto productDto, List<byte[]> imageDataList) throws Exception;


    //    to get all products
    List<Product> getAllProducts(int page);

    //    to get all products by category
    List<Product> getAllProductsByCategory(Long categoryId,int page);

    //    to get all products by sub category
    List<Product> getAllProductsBySubCategory(Long subCategoryId,int page);


//    to get product by prime
    List<Product> getPrimeProducts(int page);

//    to get product by amazon made
    List<Product> getMadeForAmazonProducts(int page);

    //    to get product by cod available
    List<Product> getCodAvailable(int page);
}
