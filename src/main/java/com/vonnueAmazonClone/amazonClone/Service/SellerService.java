package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SellerDto;
import com.vonnueAmazonClone.amazonClone.Model.Seller;

public interface SellerService {
    Seller saveSeller(SellerDto sellerDto);
    Seller updateSeller(Long id, SellerDto sellerDto);
    void deleteSeller(Long id);
}