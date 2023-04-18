package com.masai.team4.service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.masai.team4.exception.LectureException;
import com.masai.team4.exception.ResourceNotFoundException;
import com.masai.team4.repository.BookmarkRepo;
import com.masai.team4.repository.LecturesRepo;
import com.masai.team4.repository.UserRepository;
import com.masai.team4.repository.VideoRepo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.team4.dto.LecturesResponseDto;
import com.masai.team4.entities.Batch;
import com.masai.team4.entities.Bookmarks;
import com.masai.team4.entities.Category;
import com.masai.team4.entities.LectureType;
import com.masai.team4.entities.Lectures;
import com.masai.team4.entities.Section;
import com.masai.team4.entities.User;

@Service
public class BookmarkServiceImpl implements BookmarkService{
	
	@Autowired
	private BookmarkRepo bookmarkRepository;
	@Autowired
	private LecturesRepo lectureDao;
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
	public String saveBookmark(Bookmarks bookmarks) {

		bookmarkRepository.save(bookmarks);
		
		return "success";
	}

	@Override
	public List<Integer> lectureByUserId(Integer userId) {

		List<Integer> lectureId= this.bookmarkRepository.findByUserId(userId);

		return lectureId;
	}



	@Override
	public void  deleteLectureFromBookmark(Integer userId, Integer lectureId) {
		Bookmarks bookmarks= this.bookmarkRepository.findByUserIdAndLectureId(userId, lectureId);
		this.bookmarkRepository.delete(bookmarks);
	}

	public LecturesResponseDto getLectureById(Integer id) throws LectureException {
		Optional<Lectures> lectureOptional = lectureDao.findById(id);
		Map<Integer, Category> categoryMap = categoryserviceimpl.categoryMap();
		Map<Integer, Batch> batchMap = batchServiceimpl.batchMap();
		Map<Integer, Section> sectionMap = sectionServiceimpl.sectionMap();
		Map<Integer, LectureType> lectureTypeMap = lectureTypeimpl.lectureTypeMap();
		Map<Integer, User> userMap = userServiceimpl.userMap();
		LecturesResponseDto lecturesResponseDto = new LecturesResponseDto();
		
		if (lectureOptional.isPresent()) {
			if (!lectureOptional.get().isDeleteStatus()) {
				Lectures lecture = lectureOptional.get();
				Batch batch = batchMap.get(lecture.getBatch());
				Section section = sectionMap.get(lecture.getSection());
				Category category = categoryMap.get(lecture.getCategory());
				LectureType type = lectureTypeMap.get(lecture.getType());
				User user = userMap.get(lecture.getCreatedBy());
				lecturesResponseDto.setLectureId(lecture.getLectureId());
				lecturesResponseDto.setTitle(lecture.getTitle());
				lecturesResponseDto.setConcludes(lecture.getConcludes());
				lecturesResponseDto.setNotes(lecture.getNotes());
				lecturesResponseDto.setTags(lecture.getTags());
				lecturesResponseDto.setZoomLink(lecture.getZoomLink());
				lecturesResponseDto.setDay(lecture.getDay());
				lecturesResponseDto.setWeek(lecture.getWeek());
				lecturesResponseDto.setSchedule(lecture.getSchedule());
				lecturesResponseDto.setBatch(batch.getBatch());
				lecturesResponseDto.setSection(section.getSection());
				lecturesResponseDto.setCategory(category.getCategoryName());
				lecturesResponseDto.setType(type.getType());
				lecturesResponseDto.setCreatedBy(user.getName());
				if (lecture.getUpdatedBy() != null) {
					User user1 = userMap.get(lecture.getUpdatedBy());
					lecturesResponseDto.setCreatedBy(user1.getName());
				}
				lecturesResponseDto.setCopyLectureFrom(lecture.getCopyLectureFrom());
				lecturesResponseDto.setOptional(lecture.getOptional());
				lecturesResponseDto.setVideo(lecture.getVideo());
				
			}
			return lecturesResponseDto;

		} else {
			throw new LectureException("Lecture not found with ID: " + id);
		}

	}

}
