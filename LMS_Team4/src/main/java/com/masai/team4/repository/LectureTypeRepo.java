package com.masai.team4.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.team4.entities.LectureType;

public interface LectureTypeRepo extends JpaRepository<LectureType,Integer> {
}
