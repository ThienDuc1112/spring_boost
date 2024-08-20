package com.example.springboot.demo.services;

import com.example.springboot.demo.DTOs.Common.ResponseObject;
import com.example.springboot.demo.DTOs.Product.CreateProductDTO;
import com.example.springboot.demo.DTOs.Product.ProductResponse;

public interface IProductService {

    void addProduct(CreateProductDTO product);
    void updateProduct(CreateProductDTO product);

    ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, Long categoryId, String keyword);
    void deleteProduct(Long productId);

}
