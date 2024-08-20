package com.example.springboot.demo.DTOs.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private List<ProductDTO> content;
    private Integer pageNumber;
    private Long totalElements;
    private Integer totalPages;

}
