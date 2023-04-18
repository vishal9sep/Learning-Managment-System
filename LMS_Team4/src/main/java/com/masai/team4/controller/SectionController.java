package com.masai.team4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.team4.entities.Section;
import com.masai.team4.exception.LectureException;
import com.masai.team4.service.SectionService;

@RestController
@RequestMapping("/api")
public class SectionController {
	@Autowired
	private SectionService sectionservice;

	@GetMapping("/sectionList")
	public ResponseEntity<List<Section>> getBatchList() {

		return new ResponseEntity<List<Section>>(sectionservice.getSectionList(), HttpStatus.OK);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	 @PostMapping("/addsection")
		public ResponseEntity<Section> registerBatchHandler(@RequestBody Section section) {

		 Section addbatch = sectionservice.registerSection(section);

		return new ResponseEntity<Section>(addbatch, HttpStatus.CREATED);
	}


	@PostMapping("/removesection/{sectionId}")
	public ResponseEntity<Map<String, String>> deleteLectureHandler(@PathVariable("sectionId") Integer sectionId)
			throws LectureException {

		String message = sectionservice.deleteSectionById(sectionId);

		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
}
