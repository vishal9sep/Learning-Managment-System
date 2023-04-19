package com.masai.team4.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.masai.team4.entities.Batch;
@Repository
public interface BatchRepo extends JpaRepository<Batch,Integer> {
	@Query(value="SELECT * FROM batch WHERE status='A'",nativeQuery =true)
	List<Batch> batchList();
}
