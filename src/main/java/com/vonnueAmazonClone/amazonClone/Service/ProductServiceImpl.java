package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.ProductDto;
import com.vonnueAmazonClone.amazonClone.Handle.ImageProcessingException;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Product;
import com.vonnueAmazonClone.amazonClone.Repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.jpa.domain.Specification;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        byte[] imageBlob = imageProcessing.serializeImageList(imageDataList);
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



    public void resizeAndProcessImages(MultipartFile[] files, List<byte[]> imageDataList) throws IOException {
        final long MAX_IMAGE_SIZE = 1 * 1024 * 1024; // 1 MB in bytes

        for (MultipartFile file : files) {
            if (!file.isEmpty() && file.getSize() > MAX_IMAGE_SIZE) {
                throw new RuntimeException("File size is greater than 1 MB"); // Consider a more specific exception
            }

            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizedImage = resizeImage(originalImage, type, 800, 800); // maxWidth, maxHeight

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String formatName = getFormatName(file.getContentType());
            ImageIO.write(resizedImage, formatName, baos);
            imageDataList.add(baos.toByteArray());
        }
    }

    private String getFormatName(String contentType) {
        if ("image/png".equals(contentType)) return "PNG";
        if ("image/jpeg".equals(contentType) || "image/jpg".equals(contentType)) return "JPEG";
        return "JPEG"; // Default
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int type, int maxWidth, int maxHeight) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        if (width <= maxWidth && height <= maxHeight) {
            return originalImage; // No resizing needed
        }

        // Compute new dimensions while preserving aspect ratio
        double aspectRatio = (double) originalImage.getWidth() / originalImage.getHeight();
        if (width > height) {
            width = maxWidth;
            height = (int) (maxWidth / aspectRatio);
        } else {
            height = maxHeight;
            width = (int) (maxHeight * aspectRatio);
        }

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

    @Override
    public List<Product> getProductsByCriteria(Long nestedSubCategoryId,int averageRating, String seller, String brand, Long categoryId, Long subCategoryId, int page, Boolean prime, Boolean cod, Boolean madeForAmazon, BigDecimal minPrice , BigDecimal maxPrice) throws IOException, ClassNotFoundException {
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


        return products;
    }

}
