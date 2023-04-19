package com.masai.team4.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.masai.team4.entities.Lectures;
import com.masai.team4.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.team4.dto.ApiResponse;
import com.masai.team4.dto.LecturesResponseDto;
import com.masai.team4.entities.Bookmarks;
import com.masai.team4.exception.LectureException;
import com.masai.team4.service.BookmarkService;
import com.masai.team4.service.BookmarkServiceImpl;
import com.masai.team4.service.LectureService;

@RestController
@RequestMapping("/api")
public class BookmarksController {
	
	@Autowired
	private BookmarkService bookmarkService;
	@Autowired
	private BookmarkServiceImpl bookmarkServiceimpl;

	@Autowired
	private LectureService lectureService;
	


//	save to bookmarks
	@PostMapping("/bookmark") // http://localhost:8090/api/bookmark/
	public ResponseEntity<String> saveBookmarks(@RequestBody Bookmarks bookmarks){

		String bookmarks1= this.bookmarkService.saveBookmark(bookmarks);
		return  new ResponseEntity<String>("lecture added to bookmarks",HttpStatus.CREATED);
	}


	@GetMapping("/getList/{userid}")
	public ResponseEntity<Set<Lectures>> getAllBook(@PathVariable Integer userid) throws LectureException {

		Set<Lectures> setLecture = new HashSet<>();
		List<Integer> bookmark= this.bookmarkService.lectureByUserId(userid);
		for(int i=0;i<bookmark.size();i++) {

			Lectures lecturesResponseDto= this.lectureService.getLectureById(bookmark.get(i)).orElseThrow(()-> new ResourceNotFoundException("lecuture","id",userid));

			setLecture.add(lecturesResponseDto);

//			LecturesResponseDto lectureDto =this.bookmarkServiceimpl.getLectureById(bookmark.get(i));
//			setLecture.add(lectureDto);
		}

		return new ResponseEntity<Set<Lectures>>(setLecture,HttpStatus.OK);
	}


	@DeleteMapping("/{userId}/{lectureId}")
	public ResponseEntity<ApiResponse> deleteBook(@PathVariable Integer userId,@PathVariable Integer lectureId){

		this.bookmarkService.deleteLectureFromBookmark(userId , lectureId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Lecture removed", true), HttpStatus.OK);
	}
}
