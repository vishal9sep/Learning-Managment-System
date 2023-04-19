package com.masai.team4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.team4.entities.Video;
@Repository
public interface VideoRepo extends JpaRepository<Video, Integer>{

}
