package com.vonnueAmazonClone.amazonClone.DTO;

import com.vonnueAmazonClone.amazonClone.Model.Seller;
import com.vonnueAmazonClone.amazonClone.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private String message;
    private Seller seller;
    private User user;

    public SignInResponseDto(String s, User user) {
        this.message=s;
        this.user=user;
    }

    public SignInResponseDto(String s, Seller seller) {
        this.message=s;
        this.seller=seller;
    }
}
