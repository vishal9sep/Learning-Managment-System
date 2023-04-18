package com.masai.team4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.team4.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
