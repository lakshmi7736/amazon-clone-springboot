package com.vonnueAmazonClone.amazonClone.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "description", length = 1000)
    private String description;


    private BigDecimal price;

    @Lob
    @Column(columnDefinition = "LONGBLOB",name ="image_blob",length = Integer.MAX_VALUE)
    private byte[] imageBlob;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private Subcategory subCategoryId;

    @ManyToOne
    private Seller seller;


    private double averageRating;

}
