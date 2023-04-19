package com.masai.team4.service;

import java.util.List;

import com.masai.team4.dto.LecturesResponseDto;
import com.masai.team4.exception.LectureException;

public interface DashboardService {
	
	public List<LecturesResponseDto> getLecturesForCurrentDate(Integer sectionId , Integer batchId) throws LectureException;

}
