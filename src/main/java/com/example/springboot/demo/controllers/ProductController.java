package com.example.springboot.demo.controllers;

import com.example.springboot.demo.DTOs.Common.ResponseObject;
import com.example.springboot.demo.DTOs.Product.CreateProductDTO;
import com.example.springboot.demo.DTOs.Product.ProductResponse;
import com.example.springboot.demo.models.Product;
import com.example.springboot.demo.repositories.ProductRepository;
import com.example.springboot.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
    //    @Autowired
//    private ProductRepository repository;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product = new Product("aa", 2000, 12.223, "abc");
        product.setId(1L);
        products.add(product);
        return products;
    }

    @GetMapping("/get")
    ResponseEntity<ResponseObject> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        try {
            ProductResponse response = productService.getAllProduct(pageNumber, pageSize, categoryId, keyword);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "query product successfully", response)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("fail", e.getMessage(), "")
            );
        }
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@ModelAttribute CreateProductDTO newProduct) {
        try {
            productService.addProduct(newProduct);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Insert product successfully", "")
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", exception.getMessage(), "")
            );
        }
    }
    @PutMapping("/update")
    ResponseEntity<ResponseObject> updateProduct(@ModelAttribute CreateProductDTO productDTO) {
        try{
            productService.updateProduct(productDTO);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Update product successfully", productDTO)
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", e.getMessage(), "")
            );
        }
    }

    @DeleteMapping("/delete")
    ResponseEntity<ResponseObject> deleteProduct(@RequestParam(name = "id") Long id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success","Delete the product with id: " + id + "successfully","")
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail",e.getMessage() + id, "")
            );
        }
    }


}
