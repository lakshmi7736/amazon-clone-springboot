package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SellerDto;
import com.vonnueAmazonClone.amazonClone.Model.Product;
import com.vonnueAmazonClone.amazonClone.Model.Seller;

import java.util.List;


public interface SellerService {
    Seller saveSeller(SellerDto sellerDto);
    Seller updateSeller(Long id, SellerDto sellerDto);
    void deleteSeller(Long id);


}