package com.example.springboot.demo.controllers;

import com.example.springboot.demo.models.Category;
import com.example.springboot.demo.models.Product;
import com.example.springboot.demo.repositories.CategoryRepository;
import com.example.springboot.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/products")
public class MyProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @RequestMapping(value = "/getProductsByCategoryId/{categoryId}", method = RequestMethod.GET)
    public String getProductsByCategory(ModelMap modelMap, @PathVariable Long categoryId){
        Iterable<Product> products = productRepository.findByCategoryId(categoryId);
        modelMap.addAttribute("products", products);
        return "listProduct";
    }

    @RequestMapping(value = "/changeCategory/{productId}", method = RequestMethod.GET)
    public String changeCategory(ModelMap modelMap, @PathVariable Long productId){
        Iterable<Category> categories = categoryRepository.findAll();
        Optional<Product> product = productRepository.findById(productId);
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("product",product.get());
        return "changeCategory";
    }

    @RequestMapping(value = "/updateProduct/{productId}")
    public String updateProduct(@RequestParam("categoryId") Long categoryId, @PathVariable Long productId){
        Category foundCategory = categoryRepository.findById(categoryId).get();
        if (productRepository.findById(productId).isPresent() && foundCategory != null) {
            Product foundProduct = productRepository.findById(productId).get();
            foundProduct.setCategory(foundCategory);
            productRepository.save(foundProduct);
        }

        return "redirect:/products/getProductsByCategoryId/"+ foundCategory.getId();
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String addProduct(ModelMap modelMap) {
        Iterable<Category> categories = categoryRepository.findAll();
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("product", new Product());
        return "addProduct";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") Product product, @RequestParam("categoryId") Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        product.setCategory(category)
        ;
         productRepository.save(product);
         return "redirect:/categories";
    }

}
