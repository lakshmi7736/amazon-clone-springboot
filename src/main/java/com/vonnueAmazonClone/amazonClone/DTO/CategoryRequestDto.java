package com.vonnueAmazonClone.amazonClone.DTO;

import com.vonnueAmazonClone.amazonClone.Model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {
    private Long requestId;
    private String suggestedCategoryName;
    private String status;
    private Seller createdBy;
    private LocalDateTime createdAt;
}
