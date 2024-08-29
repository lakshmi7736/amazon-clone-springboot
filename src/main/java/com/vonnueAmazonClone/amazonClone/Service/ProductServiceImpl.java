package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.ProductDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Product;
import com.vonnueAmazonClone.amazonClone.Repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.jpa.domain.Specification;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


import static com.vonnueAmazonClone.amazonClone.Service.imageProcessing.deserializeImageBlob;
import static com.vonnueAmazonClone.amazonClone.Service.imageProcessing.serializeImageList;


@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public ProductDto processAndSaveProduct(ProductDto productDto, List<byte[]> imageDataList) throws Exception {
        byte[] imageBlob = serializeImageList(imageDataList);
        Product product= new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setBrand(productDto.getBrand());
        product.setImageBlob(imageBlob);

        product.setCod(productDto.isCod());
        product.setPrime(productDto.isPrime());
        product.setMadeForAmazon(productDto.isMadeForAmazon());


        product.setCategoryId(productDto.getCategoryId());
        product.setSubCategoryId(productDto.getSubCategoryId());
        product.setNestedSubCategoryId(productDto.getNestedSubCategoryId());
        product.setSeller(productDto.getSeller());
        product.setAverageRating(productDto.getAverageRating());
        productRepository.save(product);
        productDto.setId(product.getId());
        return productDto;
    }



    @Override
     public void resizeAndProcessImages(List<byte[]> imageDataList, MultipartFile file) throws IOException {
            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            int maxWidth = 800; // Adjust the maximum width as needed
            int maxHeight = 800; // Adjust the maximum height as needed
            // ... Image resizing logic (if required) ...
            imageDataList.add(file.getBytes());
        }

    @Override
    public List<ProductDto> getProductsByCriteria(Long nestedSubCategoryId,int averageRating, String seller, String brand, Long categoryId, Long subCategoryId, int page, Boolean prime, Boolean cod, Boolean madeForAmazon, BigDecimal minPrice , BigDecimal maxPrice) throws IOException, ClassNotFoundException {
        PageRequest pageRequest = PageRequest.of(page, 20); // Example with fixed page size

        Specification<Product> spec = Specification.where(null);

        if(seller !=null){
            spec =spec.and(ProductSpecifications.hasSeller(seller));
        }

        if(averageRating!=0){
            spec =spec.and(ProductSpecifications.hasRating(averageRating));
        }

        if(brand !=null){
            spec =spec.and(ProductSpecifications.hasBrand(brand));
        }

        if(categoryId !=null){
            spec = spec.and(ProductSpecifications.hasCategoryId(categoryId));
        }

        if (subCategoryId != null) {
            spec = spec.and(ProductSpecifications.hasSubCategoryId(subCategoryId));
        }
        if (nestedSubCategoryId != null) {
            spec = spec.and(ProductSpecifications.hasNestedSubCategoryId(nestedSubCategoryId));
        }
        if (prime != null) {
            spec = spec.and(ProductSpecifications.isPrime(prime));
        }
        if (cod != null) {
            spec = spec.and(ProductSpecifications.isCodAvailable(cod));
        }
        if (madeForAmazon != null) {
            spec = spec.and(ProductSpecifications.isMadeForAmazon(madeForAmazon));
        }
        // Price filtering
        if (minPrice != null && BigDecimal.ZERO.compareTo(minPrice) < 0) { // Checks if minPrice is greater than 0
            spec = spec.and(ProductSpecifications.hasPriceGreaterThan(minPrice));
        }
        if (maxPrice != null && BigDecimal.ZERO.compareTo(maxPrice) < 0) { // Checks if maxPrice is greater than 0
            spec = spec.and(ProductSpecifications.hasPriceLessThan(maxPrice));
        }

        List<Product> products = productRepository.findAll(spec, pageRequest).getContent();

        List<ProductDto> productDTOs = new ArrayList<>();
        for (Product product : products) {
            String encodedImage = null;
            if (product.getImageBlob() != null) {
                try {
                    List<byte[]> imageDataList = deserializeImageBlob(product.getImageBlob());
                    if (!imageDataList.isEmpty()) {
                        encodedImage = Base64.getEncoder().encodeToString(imageDataList.get(0));
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new InvalidDetailException("Error found can't fetch products.");
                }
            }
            productDTOs.add(new ProductDto(product, encodedImage));
        }
        return productDTOs;
    }


}
