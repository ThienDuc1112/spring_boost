package com.example.springboot.demo.mapper;

import com.example.springboot.demo.DTOs.Product.CreateProductDTO;
import com.example.springboot.demo.DTOs.Product.ProductDTO;
import com.example.springboot.demo.DTOs.Product.UserDTO;
import com.example.springboot.demo.models.Image;
import com.example.springboot.demo.models.Product;
import com.example.springboot.demo.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ProductMapper {
    ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
    default List<String> mapImageUrls(List<Image> images) {
        if (images == null) {
            return Arrays.asList("a","b","c");
        }
        return images.stream().map(Image::getFileName).collect(Collectors.toList());
    }
    @Mapping(target = "images", ignore = true)
    CreateProductDTO mapToCreateProductDTO(Product product);
    @Mapping(target = "images", ignore = true)
    Product mapToProduct(CreateProductDTO createProductDTO);
    @Mapping(source = "product.category.categoryName", target = "categoryName")
    @Mapping(target = "imageUrls", expression = "java(mapper.mapImageUrls(product.getImages()))")

    ProductDTO mapToProductDTO(Product product);
    @Mapping(target = "roles", ignore = true)
    User mapToUser(UserDTO userDTO);
}
