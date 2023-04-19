package com.masai.team4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.masai.team4.entities.Batch;
import com.masai.team4.entities.Section;
@Repository
public interface SectionRepo extends JpaRepository<Section , Integer>{
	@Query(value="SELECT * FROM section WHERE status='A'",nativeQuery =true)
	List<Section> sectionList();
}
