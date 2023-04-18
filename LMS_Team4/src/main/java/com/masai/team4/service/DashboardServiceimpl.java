package com.masai.team4.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.team4.dto.LecturesResponseDto;
import com.masai.team4.entities.Batch;
import com.masai.team4.entities.Category;
import com.masai.team4.entities.LectureType;
import com.masai.team4.entities.Lectures;
import com.masai.team4.entities.Section;
import com.masai.team4.entities.User;
import com.masai.team4.exception.LectureException;
import com.masai.team4.repository.LecturesRepo;

@Service
public class DashboardServiceimpl implements DashboardService {

	@Autowired
	private LecturesRepo lectureRepo;
	@Autowired
	private CategoryImpl categoryserviceimpl;
	@Autowired
	private BatchServiceImpl batchServiceimpl;
	@Autowired
	private SectionServiceImpl sectionServiceimpl;
	@Autowired
	private LectureTypeImpl lectureTypeimpl;
	@Autowired
	private UserServiceImpl userServiceimpl;

	@Override
	public List<LecturesResponseDto> getLecturesForCurrentDate(Integer sectionId, Integer batchId)
			throws LectureException {

		LocalDate currentDate = LocalDate.now();
		List<Lectures> lectures = lectureRepo.findByStartDateBetween(currentDate.atStartOfDay(),
				currentDate.plusDays(1).atStartOfDay(), batchId, sectionId);

		Map<Integer, Category> categoryMap = categoryserviceimpl.categoryMap();
		Map<Integer, Batch> batchMap = batchServiceimpl.batchMap();
		Map<Integer, Section> sectionMap = sectionServiceimpl.sectionMap();
		Map<Integer, LectureType> lectureTypeMap = lectureTypeimpl.lectureTypeMap();
		Map<Integer, User> userMap = userServiceimpl.userMap();

		List<LecturesResponseDto> response = new ArrayList<>();
		for (Lectures lecture : lectures) {
			if (!lecture.isDeleteStatus()) {
				Batch batch1 = batchMap.get(lecture.getBatch());
				Section section2 = sectionMap.get(lecture.getSection());
				Category category3 = categoryMap.get(lecture.getCategory());
				LectureType type4 = lectureTypeMap.get(lecture.getType());
				User user = userMap.get(lecture.getCreatedBy());
				LecturesResponseDto lecturesResponseDto = new LecturesResponseDto();

				lecturesResponseDto.setBatch(batch1.getBatch());
				lecturesResponseDto.setCategory(category3.getCategoryName());
				lecturesResponseDto.setConcludes(lecture.getConcludes());
				lecturesResponseDto.setDay(lecture.getDay());
				lecturesResponseDto.setLectureId(lecture.getLectureId());
				lecturesResponseDto.setNotes(lecture.getNotes());
				lecturesResponseDto.setSchedule(lecture.getSchedule());
				lecturesResponseDto.setSection(section2.getSection());
				lecturesResponseDto.setTags(lecture.getTags());
				lecturesResponseDto.setTitle(lecture.getTitle());
				lecturesResponseDto.setCopyLectureFrom(lecture.getCopyLectureFrom());
				lecturesResponseDto.setCreatedBy(user.getName());
				if (lecture.getUpdatedBy() != null) {
					User user1 = userMap.get(lecture.getUpdatedBy());
					lecturesResponseDto.setUpdatedBy(user1.getName());
				}
				lecturesResponseDto.setWeek(lecture.getWeek());
				lecturesResponseDto.setType(type4.getType());
				lecturesResponseDto.setZoomLink(lecture.getZoomLink());
				lecturesResponseDto.setOptional(lecture.getOptional());

				response.add(lecturesResponseDto);
			}
		}
		return response;

	}

}
