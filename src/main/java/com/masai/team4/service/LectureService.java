package com.masai.team4.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.masai.team4.dto.LectureDTO;
import com.masai.team4.dto.LecturesPagination;
import com.masai.team4.dto.LecturesResponseDto;
import com.masai.team4.entities.Lectures;
import com.masai.team4.exception.LectureException;

public interface LectureService {
	public String registerNewLecture(LectureDTO lecturedto) throws LectureException;

	public Optional<Lectures> getLectureById(Integer lecturid) throws LectureException;

	public String deleteLectureById(Integer lectureId) throws LectureException;

	public String updateLectureById(Integer lecturid, LectureDTO lecturedto) throws LectureException;

	public LecturesPagination getAllLectures(Integer pageNumber, Integer pageSize) throws LectureException;

	public List<LecturesResponseDto> searchLectures(String title, Integer batch, Integer section, Integer type,
			Integer user, String day, String week, LocalDate sdate, Integer category, Boolean optional)
			throws LectureException;

	public String copyLecture(Integer lectureId, LectureDTO lecturedto) throws LectureException;

	public List<LecturesResponseDto> getLecturesBySctionAndBatch(Integer sectionId, Integer batchId)
			throws LectureException;

	public List<LecturesResponseDto> searchLecturesforstudent(String title, Integer batch, Integer section,
			Integer type, Integer createdBy, String day, String week, LocalDate sdate, Integer category,
			Boolean optional) throws LectureException;

	public String addVideoToLecture(Integer lectureId, MultipartFile file) throws IOException, LectureException;

	public byte[] getVideoForLecture(Integer lectureId);

}
