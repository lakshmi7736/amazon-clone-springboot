package com.vonnueAmazonClone.amazonClone.DTO;

import com.vonnueAmazonClone.amazonClone.Model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;

    private String name;

    private String description;


    private BigDecimal price;


    private String encodedImage; // Base64 encoded image

    private String brand;

    private boolean prime;

    private boolean cod;

    private boolean madeForAmazon;

    private Category categoryId;

    private Subcategory subCategoryId;

    private NestedSubCategory nestedSubCategoryId;

    private Seller seller;

    private int averageRating;


    public ProductDto(Product product, String encodedImage) {
        this.name= product.getName();
        this.description= product.getDescription();
        this.price=product.getPrice();
        this.encodedImage=encodedImage;
        this.brand= product.getBrand();
        this.prime=product.isPrime();
        this.cod=product.isCod();
        this.madeForAmazon=product.isMadeForAmazon();
        this.categoryId=product.getCategoryId();
        this.subCategoryId=product.getSubCategoryId();
        this.nestedSubCategoryId=product.getNestedSubCategoryId();
        this.seller=product.getSeller();
        this.averageRating=product.getAverageRating();
    }
}
