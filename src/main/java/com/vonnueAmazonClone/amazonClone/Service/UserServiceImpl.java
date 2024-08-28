package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.UserDto;
import com.vonnueAmazonClone.amazonClone.Handle.InvalidDetailException;
import com.vonnueAmazonClone.amazonClone.Model.User;
import com.vonnueAmazonClone.amazonClone.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final Validation validation;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Validation validation, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.validation = validation;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(UserDto userDto) {
        // Check if the email is already taken by another user
        if (validation.findByEmail(userDto.getEmail()) != null) {
            throw new InvalidDetailException("Email is already taken.");
        }
        if (!validation.isValidEmail(userDto.getEmail())) {
            throw new InvalidDetailException("Invalid email format.");
        }
        String pass= passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(pass);
        User user = new User();
        // Copies all matching properties from sellerDto to seller no need of seller = new Seller(); seller.setId(sellerDto.getId());
        BeanUtils.copyProperties(userDto, user);

        return userRepository.save(user);
    }


    @Override
    public User updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Check if email is changed and not taken by another user
        if (userDto.getEmail() != null && !userDto.getEmail().equals(existingUser.getEmail())) {
            if (validation.findByEmail(userDto.getEmail()) != null) {
                throw new InvalidDetailException("Email is already taken.");
            }
            if (!validation.isValidEmail(userDto.getEmail())) {
                throw new InvalidDetailException("Invalid email format.");
            }
            existingUser.setEmail(userDto.getEmail());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id: " + id));
        userRepository.deleteById(id);
    }

}
