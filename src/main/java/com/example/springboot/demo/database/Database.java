package com.example.springboot.demo.database;

import com.example.springboot.demo.models.Category;
import com.example.springboot.demo.models.Product;
import com.example.springboot.demo.models.Role;
import com.example.springboot.demo.repositories.CategoryRepository;
import com.example.springboot.demo.repositories.ProductRepository;
import com.example.springboot.demo.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository, CategoryRepository categoryRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //Category category = new Category("Laptop","a device for working");
               // categoryRepository.save(category);
//                Product productA = new Product("Macbook Pro M1", 2023, 230.1, "url");
//                Product productB = new Product("Dell XP MMA", 2022, 120.1, "url");
//                productA.setCategory(category);
//                productB.setCategory(category);
//                Role role = new Role();
//                role.setRoleName("user");
//                roleRepository.save(role);
            }
        };
    }
}
