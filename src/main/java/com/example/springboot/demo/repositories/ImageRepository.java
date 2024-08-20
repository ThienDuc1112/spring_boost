package com.example.springboot.demo.repositories;

import com.example.springboot.demo.models.Image;
import com.example.springboot.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {

}
