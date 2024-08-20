package com.example.springboot.demo.controllers;

import com.example.springboot.demo.DTOs.Common.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/images")
public class ImageController {
    @GetMapping("/path")
    ResponseEntity<ResponseObject> GetString(){
        List<String> strings = new ArrayList<>();
        strings.add("aaa");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Insert product successfully", strings)
        );
    }
}
