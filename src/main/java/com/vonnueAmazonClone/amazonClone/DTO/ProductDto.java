package com.vonnueAmazonClone.amazonClone.DTO;

import com.vonnueAmazonClone.amazonClone.Model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;

    private String name;

    private String description;


    private BigDecimal price;


    private String brand;

    private boolean prime;

    private boolean cod;

    private boolean madeForAmazon;

    private Category categoryId;

    private Subcategory subCategoryId;

    private NestedSubCategory nestedSubCategoryId;

    private Seller seller;

    private int averageRating;


}
