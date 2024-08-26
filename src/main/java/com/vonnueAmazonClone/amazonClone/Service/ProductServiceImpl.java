package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.ProductDto;
import com.vonnueAmazonClone.amazonClone.Handle.ImageProcessingException;
import com.vonnueAmazonClone.amazonClone.Model.Product;
import com.vonnueAmazonClone.amazonClone.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
        product.setImageBlob(imageBlob);
        product.setCategoryId(productDto.getCategoryId());
        product.setSubCategoryId(productDto.getSubCategoryId());
        product.setSeller(productDto.getSeller());
        product.setAverageRating(productDto.getAverageRating());
        productRepository.save(product);
        productDto.setId(product.getId());
        productDto.setImageBlob(product.getImageBlob());
        return productDto;
    }

    @Override
    public void resizing(MultipartFile[] files) {
        System.out.println("Hey I reached resizing service");
        List<byte[]> imageDataList = new ArrayList<>();
        final long MAX_IMAGE_SIZE = 1 * 1024 * 1024; // 1 MB in bytes

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                if (file.getSize() > MAX_IMAGE_SIZE) {
                    throw new ImageProcessingException("File size is greater than 1 MB");
                }

                try {
                    System.out.println("Hey I trying for resizeAndAddImage");
                    resizeAndAddImage(imageDataList, file);
                } catch (Exception e) {
                    // Correctly instantiate and throw your custom exception
                    throw new ImageProcessingException("Error processing image", e);
                }
            }
        }
    }


    private void resizeAndAddImage(List<byte[]> imageDataList, MultipartFile file) throws IOException {
        System.out.println("Hey I reached resizeAndAddImage");
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
        int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        int maxWidth = 800; // Adjust the maximum width as needed
        int maxHeight = 800; // Adjust the maximum height as needed

        BufferedImage resizedImage = resizeImage(originalImage, type, maxWidth, maxHeight);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Determine the output format based on the input file's content type
        String formatName = "JPEG"; // Default format
        if (file.getContentType() != null) {
            if (file.getContentType().equals("image/png")) {
                formatName = "PNG";
            } else if (file.getContentType().equals("image/jpeg")) {
                formatName = "JPEG";
            }
            else if (file.getContentType().equals("image/jpg")) {
                formatName = "JPG";
            }
        }

        ImageIO.write(resizedImage, formatName, baos); // Assuming the image format is JPEG
        byte[] bytes = baos.toByteArray();

        imageDataList.add(bytes);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int type, int maxWidth, int maxHeight) {
        System.out.println("hey I reached resizeImage");
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


}
