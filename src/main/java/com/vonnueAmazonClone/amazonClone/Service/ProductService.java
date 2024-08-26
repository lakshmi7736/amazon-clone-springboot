package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {
    void resizing(MultipartFile[] files) ;
    ProductDto processAndSaveProduct(ProductDto productDto, List<byte[]> imageDataList) throws Exception;
}
