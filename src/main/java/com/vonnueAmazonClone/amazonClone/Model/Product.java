package com.vonnueAmazonClone.amazonClone.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "products", indexes = {
        @Index(name = "idx_seller_id", columnList = "seller_id"),
        @Index(name = "idx_category_subcategory_nestedSubCategory", columnList = "category_id, sub_category_id, nested_sub_category_id"),
        @Index(name = "idx_price", columnList = "price"),
        @Index(name = "idx_is_prime", columnList = "prime"),
        @Index(name = "idx_is_cod_available", columnList = "cod"),
        @Index(name="idx_is_made_for_amazon", columnList = "made_for_amazon")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    private BigDecimal price;

    private String brand;

    private boolean prime;

    private boolean cod;

    private boolean madeForAmazon;

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
    @JoinColumn(name = "nested_sub_category_id")
    private NestedSubCategory nestedSubCategoryId;

    @ManyToOne
    private Seller seller;

    private int averageRating;

}
