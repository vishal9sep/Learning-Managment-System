package com.masai.team4.service;

import java.util.List;

import com.masai.team4.entities.Category;

public interface CategoryService {

    Category saveCategory(Category category);

    Category getCategory(Integer id);

    List<Category> getAllList();

}
