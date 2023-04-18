package com.masai.team4.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.team4.entities.Category;
import com.masai.team4.exception.ResourceNotFoundException;
import com.masai.team4.repository.CategoryRepo;

@Service
public class CategoryImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;
    
    private static List<Category> categoryList;
    
    @PostConstruct
    void init() {
    	categoryList = categoryRepo.findAll();
    	
    }

    @Override
    public Category saveCategory(Category category) {

        return this.categoryRepo.save(category);
    }

    @Override
    public Category getCategory(Integer id) {

        Category category= this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));

        return category;
    }

    @Override
    public List<Category> getAllList() {

        List<Category> categoryList= this.categoryRepo.findAll();

        return categoryList;
    }
    
    public Map<Integer, Category> categoryMap() {
		
    	Map<Integer, Category> map = new HashMap<>();
    	categoryList.forEach(c -> map.put(c.getId(), c));
    	return map;
	}
}
