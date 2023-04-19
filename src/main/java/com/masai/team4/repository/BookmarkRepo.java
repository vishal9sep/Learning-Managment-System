package com.masai.team4.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.masai.team4.entities.Bookmarks;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookmarkRepo extends JpaRepository<Bookmarks, Integer> {

    @Query("select b.lectureId from Bookmarks b where userId =:id")
    List<Integer> findByUserId(@Param("id") Integer userId);

    Bookmarks findByUserIdAndLectureId(Integer userId, Integer lectureId);
}
