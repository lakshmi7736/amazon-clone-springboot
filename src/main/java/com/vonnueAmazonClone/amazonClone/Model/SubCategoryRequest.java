package com.vonnueAmazonClone.amazonClone.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class SubCategoryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private String suggestedSubCategoryName;
    @ManyToOne
    private Category category;

    private String status;
    @ManyToOne
    private Seller createdBy;
    private LocalDateTime createdAt;
}
