package com.masai.team4.service;

import java.util.List;
import com.masai.team4.entities.Bookmarks;


public interface BookmarkService {
	
	String saveBookmark(Bookmarks bookmarks);

	List<Integer> lectureByUserId(Integer userId);

	void deleteLectureFromBookmark(Integer userId, Integer lectureId);
}
