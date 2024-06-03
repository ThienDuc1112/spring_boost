package com.example.springboot.demo.controllers;

import com.example.springboot.demo.DTOs.Common.ResponseObject;
import com.example.springboot.demo.models.Product;
import com.example.springboot.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    List<Product> getAllProducts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductById(@PathVariable Long id) {
        Optional<Product> foundProduct = repository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("success", "query product successfully", foundProduct)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("fail", "can't find product with id: " + id, "")
                );
    }

    @PostMapping("/add")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());
        if (foundProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("fail", "The name of product already existed", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Insert product successfully", repository.save(newProduct))
        );
    }

    //upsert = update if found, otherwise insert
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product updatedProduct, @PathVariable Long id) {
        Product myProduct = repository.findById(id).map(
                product -> {
                    product.setProductName(updatedProduct.getProductName());
                    product.setProductYear(updatedProduct.getProductYear());
                    product.setPrice(updatedProduct.getPrice());
                    product.setUrl(updatedProduct.getUrl());
                    return repository.save(product);
                }).orElseGet(() -> {
            updatedProduct.setId(id);
            return repository.save(updatedProduct);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Update product successfully", myProduct)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id){
        boolean exists = repository.existsById(id);
        if(exists){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success","Delete the product with id: " + id + "successfully","")
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail","not found the product with id: " + id, "")
        );
    }

}
