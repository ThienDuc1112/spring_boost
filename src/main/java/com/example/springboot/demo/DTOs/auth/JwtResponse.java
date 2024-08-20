package com.example.springboot.demo.DTOs.auth;

import java.util.List;

public class JwtResponse {
    private String token;
    private String refreshToken;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
