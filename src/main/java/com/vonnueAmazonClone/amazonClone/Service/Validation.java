package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.Model.Category;
import com.vonnueAmazonClone.amazonClone.Model.Seller;

public interface Validation {
    boolean isValidEmail(String email);
    boolean isValidPhoneNumber(String phoneNumber);
    Seller findByEmail(String email);

}
