package com.masai.team4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.masai.team4.config.AppConstants;
import com.masai.team4.dto.LectureDTO;
import com.masai.team4.dto.LectureSearchDTO;
import com.masai.team4.dto.LecturesPagination;
import com.masai.team4.dto.LecturesResponseDto;
import com.masai.team4.entities.Lectures;
import com.masai.team4.exception.LectureException;
import com.masai.team4.service.LectureService;

@RestController
@RequestMapping("/api/lecture")
public class LecturesController {
	@Autowired
	private LectureService lectureService;

	// @PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addLecture")
	public ResponseEntity<Map<String, String>> registerLectureHandler(@RequestBody LectureDTO lectureDto)
			throws IOException, LectureException {
		String message = lectureService.registerNewLecture(lectureDto);
		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/removeLecture/{lectureId}")
	public ResponseEntity<Map<String, String>> deleteLectureHandler(@PathVariable("lectureId") Integer lectureId)
			throws LectureException {

		String message = lectureService.deleteLectureById(lectureId);

		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	// @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/UpdateLecture/{lectureId}")
	public ResponseEntity<Map<String, String>> updateLectureEntityHandler(@PathVariable Integer lectureId,
			@RequestBody LectureDTO lecturedto) throws LectureException {

		try {
			String updatedLecture = lectureService.updateLectureById(lectureId, lecturedto);
			Map<String, String> response = new HashMap<>();
			response.put("message", updatedLecture);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (LectureException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/lectureList")
	public ResponseEntity<LecturesPagination> getAllLecturesHandler(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize)
			throws LectureException {

		LecturesPagination lectureDtos = lectureService.getAllLectures(pageNumber, pageSize);

		return new ResponseEntity<LecturesPagination>(lectureDtos, HttpStatus.OK);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@PostMapping("lectures/search")
	public ResponseEntity<List<LecturesResponseDto>> searchLectures(@RequestBody LectureSearchDTO lectureSearchDto,
			HttpServletRequest request, HttpServletResponse response) throws LectureException {
		List<LecturesResponseDto> lectures = lectureService.searchLectures(lectureSearchDto.getTitle(),
				lectureSearchDto.getBatch(), lectureSearchDto.getSection(), lectureSearchDto.getType(),
				lectureSearchDto.getCreatedBy(), lectureSearchDto.getDay(), lectureSearchDto.getWeek(),
				lectureSearchDto.getStartTime(), null, null);
		return ResponseEntity.ok(lectures);

	
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/lectures/{lectureId}/copy")
	public ResponseEntity<Map<String, String>> copyLecture(@PathVariable Integer lectureId,
			@RequestBody LectureDTO lecturedto) throws LectureException {
		String copiedLecture = lectureService.copyLecture(lectureId, lecturedto);
		Map<String, String> response = new HashMap<>();
		response.put("message", copiedLecture);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/lectures/{lectureId}")
	public ResponseEntity<Optional<Lectures>> getLectureByIdHandler(@PathVariable("lectureId") Integer lectureId)
			throws LectureException {
		Optional<Lectures> lecture = lectureService.getLectureById(lectureId);
		return new ResponseEntity<Optional<Lectures>>(lecture, HttpStatus.OK);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{lectureId}/video")
	public ResponseEntity<Map<String, String>> addVideoToLecture(@PathVariable("lectureId") Integer lectureId,
			@RequestParam("file") MultipartFile file) throws IOException, LectureException {
		String message = lectureService.addVideoToLecture(lectureId, file);
		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{lectureId}/video")
	public ResponseEntity<byte[]> getVideoForLecture(@PathVariable("lectureId") Integer lectureId) {
		byte[] video = lectureService.getVideoForLecture(lectureId);
		if (video != null) {
			return new ResponseEntity<>(video, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/lectureList/student/{sectionId}/{batchId}")
	public ResponseEntity<List<LecturesResponseDto>> studentLectureList(@PathVariable("sectionId") Integer sectionId,
			@PathVariable("batchId") Integer batchId) throws LectureException {

		List<LecturesResponseDto> responce = lectureService.getLecturesBySctionAndBatch(sectionId, batchId);
		return ResponseEntity.ok(responce);

	}

	@PostMapping("lectures/search/student/{sectionId}/{batchId}")
	public ResponseEntity<List<LecturesResponseDto>> studentsLectureSearch(
			@RequestBody LectureSearchDTO lectureSearchDto, @PathVariable("sectionId") Integer sectionId,
			@PathVariable("batchId") Integer batchId, HttpServletRequest request, HttpServletResponse response)
			throws LectureException {

			List<LecturesResponseDto> lectures = lectureService.searchLecturesforstudent(lectureSearchDto.getTitle(), batchId,
					sectionId, lectureSearchDto.getType(), lectureSearchDto.getCreatedBy(), null, null,
					lectureSearchDto.getStartTime(), lectureSearchDto.getCategory(), lectureSearchDto.getOptional());
			return ResponseEntity.ok(lectures);
		}

		

	
}