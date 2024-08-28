package com.vonnueAmazonClone.amazonClone.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class NestedSubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nestedSubCategoryName;
    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private Subcategory subCategoryId;
}
