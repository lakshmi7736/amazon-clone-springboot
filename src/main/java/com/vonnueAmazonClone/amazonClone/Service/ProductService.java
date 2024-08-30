package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


public interface ProductService {
     List<String> getAllUniqueBrands();

    void resizeAndProcessImages(List<byte[]> imageDataList, MultipartFile file) throws IOException;    ProductDto processAndSaveProduct(ProductDto productDto, List<byte[]> imageDataList) throws Exception;
    //    //    to get all products with or without filters
    List<ProductDto> getProductsByCriteria(Long nestedSubCategoryId,int averageRating, String seller, String brand, Long categoryId, Long subCategoryId, int page, Boolean prime, Boolean cod, Boolean madeForAmazon, BigDecimal minPrice ,BigDecimal maxPrice) throws IOException, ClassNotFoundException;

}
