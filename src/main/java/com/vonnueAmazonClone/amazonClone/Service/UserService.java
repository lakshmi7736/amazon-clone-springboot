package com.vonnueAmazonClone.amazonClone.Service;

import com.vonnueAmazonClone.amazonClone.DTO.UserDto;
import com.vonnueAmazonClone.amazonClone.Model.User;

public interface UserService {

    User saveUser(UserDto userDto);
    User updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);

    User findByEmail(String email);
}
