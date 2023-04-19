package com.masai.team4.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.masai.team4.entities.Lectures;

@Repository

public interface LecturesRepo extends JpaRepository<Lectures, Integer> {
	@Query("SELECT l FROM Lectures l WHERE " + "(:title IS NULL OR l.title LIKE %:title%) AND "
			+ "(:batch IS NULL OR l.batch = :batch) AND " + "(:section IS NULL OR l.section = :section) AND "
			+ "(:type IS NULL OR l.type = :type) AND " + "(:createdBy IS NULL OR l.createdBy LIKE :createdBy) AND"
			+ "(:day IS NULL OR l.day LIKE :day) AND" + "(:week IS NULL OR l.week LIKE :week) AND"
			+ "(:startTime IS NULL OR  date(l.schedule)=date(:startTime)) AND"
			+ "(:category IS null OR l.category=:category) AND"
			+"( :optional IS NULL OR l.optional=:optional)")
	List<Lectures> searchLectures(@Param("title") String title, @Param("batch") Integer batch,
			@Param("section") Integer section, @Param("type") Integer type, @Param("createdBy") Integer createdBy,
			@Param("day") String day, @Param("week") String week, @Param("startTime") LocalDate startTime,
			@Param("category") Integer category,@Param("optional") Boolean optional);

	@Query("SELECT l FROM Lectures l WHERE (:batch IS NULL OR l.batch = :batch) AND (:section IS NULL OR l.section = :section) AND (DATE(l.schedule)=date(:date) OR DATE(l.schedule)<=date(:date))")
	List<Lectures> getLecturesBySctionAndBatch(Integer section, Integer batch,LocalDate date);

	@Query("SELECT l FROM Lectures l WHERE " +
		       "(l.batch = :batch) AND " +
		       "(l.section = :section) AND " +
		       "(l.schedule between :cd and :ed)")
    List<Lectures> findByStartDateBetween(@Param("cd") LocalDateTime cd, @Param("ed") LocalDateTime ed, @Param("batch") Integer batch, @Param("section") Integer section);
    
}
