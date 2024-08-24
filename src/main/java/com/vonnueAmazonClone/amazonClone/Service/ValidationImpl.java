package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.Model.Category;
import com.vonnueAmazonClone.amazonClone.Model.Seller;
import com.vonnueAmazonClone.amazonClone.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationImpl implements Validation{

    private final SellerRepository sellerRepository;

    @Autowired
    public ValidationImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }


    @Override
    public boolean isValidEmail(String email) {
        // Define the pattern to match a valid email address
        String emailRegex = "^[\\w\\.-]+@gmail\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the given email address with the pattern
        Matcher matcher = pattern.matcher(email);

        // Return true if the email address matches the pattern, false otherwise
        return matcher.matches();
    }

    @Override
    public boolean isValidPhoneNumber(String phoneNumber) {
        String indianPhoneNumberRegex = "^(?:(?:\\+|0{0,2})91(\\s*[-]\\s*)?|[0]?)?[6789]\\d{9}$";
        Pattern pattern = Pattern.compile(indianPhoneNumberRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    @Override
    public Seller findByEmail(String email) {
        return sellerRepository.findByEmail(email);
    }




}
