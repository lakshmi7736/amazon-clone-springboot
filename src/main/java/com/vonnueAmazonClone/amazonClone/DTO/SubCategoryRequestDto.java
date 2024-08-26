package com.vonnueAmazonClone.amazonClone.DTO;

import com.vonnueAmazonClone.amazonClone.Model.Category;
import com.vonnueAmazonClone.amazonClone.Model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequestDto {
    private Long requestId;
    private String suggestedSubCategoryName;
    private String status;
    private Seller createdBy;
    private Category category;
    private LocalDateTime createdAt;
}
