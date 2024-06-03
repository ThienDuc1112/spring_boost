package com.example.springboot.demo.controllers;

import com.example.springboot.demo.models.Category;
import com.example.springboot.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path="/categories")
public class CategoryController {
    //inject dependency
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value="", method = RequestMethod.GET)
    public String getAllCategories(ModelMap modelMap) {
        modelMap.addAttribute("name","thien");
        modelMap.addAttribute("age",23);
        Iterable<Category> categories = categoryRepository.findAll();
        modelMap.addAttribute("categories", categories);
        return "category";
    }
}
