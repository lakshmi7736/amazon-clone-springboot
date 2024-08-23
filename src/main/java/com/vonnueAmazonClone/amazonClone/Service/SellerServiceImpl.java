package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SellerDto;
import com.vonnueAmazonClone.amazonClone.Model.Seller;
import com.vonnueAmazonClone.amazonClone.Repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Seller saveSeller(SellerDto sellerDto) {
        Seller seller = new Seller();
        // Copies all matching properties from sellerDto to seller no need of below
//        Seller seller = new Seller();
//        seller.setId(sellerDto.getId());
//        seller.setBusinessName(sellerDto.getBusinessName());
//        seller.setEmail(sellerDto.getEmail());
        BeanUtils.copyProperties(sellerDto, seller);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSeller(Long id, SellerDto sellerDto) {
        return sellerRepository.findById(id).map(seller -> {
            BeanUtils.copyProperties(sellerDto, seller);
            // Ensure the role is not overwritten
            seller.setRole("SELLER_ROLE");
            return sellerRepository.save(seller);
        }).orElseThrow(() -> new EntityNotFoundException("Seller not found with id: " + id));
    }
}