package com.example.springboot.demo.services;

import com.example.springboot.demo.DTOs.Common.ResponseObject;
import com.example.springboot.demo.DTOs.Product.CreateProductDTO;
import com.example.springboot.demo.DTOs.Product.ProductDTO;
import com.example.springboot.demo.DTOs.Product.ProductResponse;
import com.example.springboot.demo.mapper.ProductMapper;
import com.example.springboot.demo.models.Category;
import com.example.springboot.demo.models.Image;
import com.example.springboot.demo.models.Product;
import com.example.springboot.demo.repositories.CategoryRepository;
import com.example.springboot.demo.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductService implements IProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private  ImageStoreService imageStoreService;

    @Override
    public void addProduct(CreateProductDTO product) {
        Category category = categoryRepository.findById((product.getCategoryId())).orElseThrow(
                () -> new IllegalArgumentException("the category with id: " + product.getCategoryId() + " doesn't exist!")
        );

        List<Product> foundProducts = productRepository.findByProductName((product.getProductName()));
        if(foundProducts.size()>0){
            throw  new IllegalArgumentException("This product with name: " + product.getProductName()+ " has already exist");
        }

        Product newProduct = ProductMapper.mapper.mapToProduct(product);
        newProduct.setCategory(category);
        List<Image> files = new ArrayList<>();
        for(int i = 0; i < product.getImages().size(); i++){
            String fileName = imageStoreService.storeFile(product.getImages().get(i));
            Image image = new Image();
            image.setFileName(fileName);
            image.setProduct(newProduct);
            files.add(image);
        }
        newProduct.setImages(files);
        try{
            productRepository.save(newProduct);
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void updateProduct(CreateProductDTO productDTO) {
        Product foundProduct = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("The product with id: " + productDTO.getId() + " doesn't exist"));
        Category category = categoryRepository.findById((productDTO.getCategoryId())).orElseThrow(
                () -> new IllegalArgumentException("the category with id: " + productDTO.getCategoryId() + " doesn't exist!")
        );
        foundProduct.setProductName(productDTO.getProductName());
        foundProduct.setProductYear(productDTO.getProductYear());
        foundProduct.setPrice(productDTO.getPrice());
        foundProduct.setUrl(productDTO.getUrl());
        foundProduct.setCategory(category);

        productRepository.save(foundProduct);
    }

    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, Long categoryId, String keyword) {
        pageNumber = pageNumber != null ? pageNumber : 0;
//        Category category = categoryRepository.findById((categoryId)).orElseThrow(
//                () -> new IllegalArgumentException("the category with id: " + categoryId + " doesn't exist!")
//        );
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productPage;
        if (categoryId != null && keyword != null) {
            productPage = productRepository.findAllByKeywordAndCategoryId(keyword, categoryId, pageable);
        } else if (categoryId != null) {
            productPage = productRepository.findByCategoryId(categoryId, pageable);
        }else if (keyword != null) {
            productPage = productRepository.findByProductNameLike(keyword, pageable);
        }else{
            productPage = productRepository.findAll(pageable);
        }

        List<Product> products = productPage.getContent();

        List<ProductDTO> productDTOS = products.stream().map(p -> ProductMapper.mapper.mapToProductDTO(p))
                .collect(Collectors.toList());
//        List<ProductDTO> productDTOS = products.stream()
//                .map(product -> {
//                    ProductDTO productDTO = new ProductDTO(); // Create a new ProductDTO instance
//                    productDTO.setId(product.getId());
//                    productDTO.setProductName(product.getProductName()); // Set other product details
//                    productDTO.setImageUrls(mapImageUrls(product.getImages()));
//                    productDTO.setProductYear(product.getImages().size());// Use mapImageUrls
//                    return productDTO;
//                })
//                .collect(Collectors.toList());
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setTotalElements( productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        return  productResponse;
    }

    @Override
    public void deleteProduct(Long productId) {
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("The product with id: " + productId + " doesn't exist"));
        productRepository.delete(foundProduct);
    }
}
