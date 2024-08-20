package com.example.springboot.demo.repositories;

import com.example.springboot.demo.models.Category;
import com.example.springboot.demo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByProductName(String productName);
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %?1% AND p.category.id = ?2")
    Page<Product> findAllByKeywordAndCategoryId(String keyword, Long categoryId, Pageable pageable);
    Page<Product> findByProductNameLike(String keyword, Pageable pageDetails);

}
