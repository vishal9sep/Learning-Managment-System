package com.masai.team4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.team4.dto.LecturesResponseDto;
import com.masai.team4.exception.LectureException;
import com.masai.team4.service.DashboardService;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {

	@Autowired
	
	private DashboardService dashboardService;

	@GetMapping("/lectures/{batchId}/{sectionId}")
	public ResponseEntity<List<LecturesResponseDto>> getLecturesForCurrentDate(@PathVariable Integer batchId , @PathVariable Integer sectionId) throws LectureException {

		List<LecturesResponseDto> lectures = dashboardService.getLecturesForCurrentDate(sectionId , batchId);

		 return ResponseEntity.ok(lectures);
	}

}
