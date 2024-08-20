package com.example.springboot.demo.DTOs.Product;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateProductDTO implements Serializable {
    private Long id;
    private String productName;
    private int productYear;
    private Double price;
    private String url;
    private Long categoryId;
    private List<MultipartFile> images;
}
