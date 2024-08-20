package com.example.springboot.demo.DTOs.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String productName;
    private int productYear;
    private Double price;
    private String url;
    private String categoryName;
    private List<String> imageUrls;
}
