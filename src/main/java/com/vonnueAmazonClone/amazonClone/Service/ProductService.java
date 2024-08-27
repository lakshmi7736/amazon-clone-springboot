package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.ProductDto;
import com.vonnueAmazonClone.amazonClone.Model.Product;
import com.vonnueAmazonClone.amazonClone.Model.Subcategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {
    void resizing(MultipartFile[] files) ;
    ProductDto processAndSaveProduct(ProductDto productDto, List<byte[]> imageDataList) throws Exception;
    List<Product> getProductsByCriteria(Long categoryId,Long subCategoryId, int page, Boolean prime, Boolean cod, Boolean madeForAmazon) ;//    //    to get all products

}
