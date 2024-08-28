package com.vonnueAmazonClone.amazonClone.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;

    private String email;

    private String phoneNumber;

    private String gst;

    private String pan_card;

    private String role= "SELLER_ROLE";

    private String bankAccountNumber;

    private String password;
}
