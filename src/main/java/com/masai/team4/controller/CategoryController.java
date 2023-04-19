package com.masai.team4.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.masai.team4.entities.Category;
import com.masai.team4.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController{

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){

        Category category1= this.categoryService.saveCategory(category);

        return new ResponseEntity<Category>(category1, HttpStatus.CREATED);
    } 


    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Integer id){

        Category category=this.categoryService.getCategory(id);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }


    @GetMapping("/categoryList")
    public ResponseEntity<List<Category>> getCategoryList(){

        List<Category> category=this.categoryService.getAllList();

        return new ResponseEntity<>(category,HttpStatus.OK);
    }

}
