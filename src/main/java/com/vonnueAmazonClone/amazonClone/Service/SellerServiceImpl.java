package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.SellerDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.Seller;
import com.vonnueAmazonClone.amazonClone.Repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class SellerServiceImpl implements SellerService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final Validation validation;

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(BCryptPasswordEncoder passwordEncoder, Validation validation, SellerRepository sellerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.validation = validation;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Seller saveSeller(SellerDto sellerDto) {
        // Check if the email is already taken by another user
        if (validation.findByEmail(sellerDto.getEmail()) != null) {
            throw new InvalidDetailException("Email is already taken.");
        }
        if (!validation.isValidEmail(sellerDto.getEmail())) {
            throw new InvalidDetailException("Invalid email format.");
        }
        if (!validation.isValidPhoneNumber(sellerDto.getPhoneNumber())) {
            throw new InvalidDetailException("Invalid phone number format.");
        }
        String pass = passwordEncoder.encode(sellerDto.getPassword());
        sellerDto.setPassword(pass);
        Seller seller = new Seller();
        // Copies all matching properties from sellerDto to seller
        BeanUtils.copyProperties(sellerDto, seller);

        return sellerRepository.save(seller);
    }


    @Override
    public Seller updateSeller(Long id, SellerDto sellerDto) {
        Seller existingSeller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id: " + id));

        // Check if email is changed and not taken by another user
        if (sellerDto.getEmail() != null && !sellerDto.getEmail().equals(existingSeller.getEmail())) {
            if (validation.findByEmail(sellerDto.getEmail()) != null) {
                throw new InvalidDetailException("Email is already taken.");
            }
            if (!validation.isValidEmail(sellerDto.getEmail())) {
                throw new InvalidDetailException("Invalid email format.");
            }
            existingSeller.setEmail(sellerDto.getEmail());
        }

        // Check and update phoneNumber if it has changed
        if (sellerDto.getPhoneNumber() != null && !sellerDto.getPhoneNumber().equals(existingSeller.getPhoneNumber())) {
            if (!validation.isValidPhoneNumber(sellerDto.getPhoneNumber())) {
                throw new InvalidDetailException("Invalid phone number format.");
            }
            existingSeller.setPhoneNumber(sellerDto.getPhoneNumber());
        }

        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id: " + id));
        sellerRepository.deleteById(id);
    }



}