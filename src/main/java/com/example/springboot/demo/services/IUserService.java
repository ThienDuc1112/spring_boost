package com.example.springboot.demo.services;

import com.example.springboot.demo.DTOs.Product.UserDTO;
import com.example.springboot.demo.exceptions.APIException;
import com.example.springboot.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {
    UserDTO register(UserDTO userDTO);

}
