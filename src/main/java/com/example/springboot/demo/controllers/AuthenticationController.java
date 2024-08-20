package com.example.springboot.demo.controllers;

import com.example.springboot.demo.DTOs.Common.ResponseObject;
import com.example.springboot.demo.DTOs.Product.UserDTO;
import com.example.springboot.demo.DTOs.auth.LoginCredentials;
import com.example.springboot.demo.services.UserService;
import com.example.springboot.demo.utils.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/authentication")
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    ResponseEntity<ResponseObject> register(@Valid @RequestBody UserDTO userDTO){
        try{
            String encodedPass = passwordEncoder.encode(userDTO.getPassword());
            userDTO.setPassword(encodedPass);

            UserDTO res = userService.register(userDTO);
            String token = jwtUtil.generateToken(res.getEmail());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("success", "register a new account successfully", token));
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", exception.getMessage(), ""));
        }
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@Valid @RequestBody LoginCredentials credentials) {

        UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(
                credentials.getEmail(), credentials.getPassword());

        authenticationManager.authenticate(authCredentials);

        String token = jwtUtil.generateToken(credentials.getEmail());

        return Collections.singletonMap("jwt-token", token);
    }

}
