package com.vonnueAmazonClone.amazonClone.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CategoryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private String suggestedCategoryName;
    private String status;

    @ManyToOne
    private Seller createdBy;
    private LocalDateTime createdAt;
}
