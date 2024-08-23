package com.vonnueAmazonClone.amazonClone.DTO;

import com.vonnueAmazonClone.amazonClone.Model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    private String name;

    private String image;

    private Seller seller;

}
