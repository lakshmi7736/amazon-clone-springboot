package com.vonnueAmazonClone.amazonClone.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerDto {
    private Long id;

    private String businessName;

    private String email;

    private String phoneNumber;

    private String gst;

    private String pan_card;

    private String bankAccountNumber;
}
