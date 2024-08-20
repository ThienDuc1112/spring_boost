package com.example.springboot.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/public")
public class CategoryControlller {
    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        String data = "data";
        return ResponseEntity.status(200).body(data);
    }
}
