package com.vonnueAmazonClone.amazonClone.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subCategoryName;

    @ManyToOne
    private Category categoryId;
}
