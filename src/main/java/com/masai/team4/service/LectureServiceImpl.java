package com.masai.team4.service;

import java.awt.print.Pageable;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.masai.team4.dto.LectureDTO;
import com.masai.team4.dto.LecturesPagination;
import com.masai.team4.dto.LecturesResponseDto;
import com.masai.team4.entities.Batch;
import com.masai.team4.entities.Category;
import com.masai.team4.entities.LectureType;
import com.masai.team4.entities.Lectures;
import com.masai.team4.entities.Section;
import com.masai.team4.entities.User;
import com.masai.team4.entities.Video;
import com.masai.team4.exception.LectureException;
import com.masai.team4.repository.LecturesRepo;
import com.masai.team4.repository.UserRepository;
import com.masai.team4.repository.VideoRepo;

@Service
public class LectureServiceImpl implements LectureService {

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
	private UserRepository userRepository;
	@Autowired
	private UserServiceImpl userServiceimpl;
	@Autowired
	private VideoRepo videoRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String registerNewLecture(LectureDTO lecturedto) throws LectureException {
		Lectures lecture = modelMapper.map(lecturedto, Lectures.class);
		lectureDao.save(lecture);
		return "Lecture created successfully";

	}

	@Override
	@Transactional
	public String deleteLectureById(Integer lectureid) throws LectureException {

		Lectures lecture = lectureDao.findById(lectureid)
				.orElseThrow(() -> new LectureException("Lecture does not exists or already deleted"));
		lecture.setDeleteStatus(true);
		return "Delete Done";

	}

	@Override
	public String updateLectureById(Integer lecturid, LectureDTO lecturedto) throws LectureException {

		Optional<Lectures> lectureOptional = lectureDao.findById(lecturid);

		if (lectureOptional.isPresent()) {
			Lectures lecture = lectureOptional.get();
			lecture.setTitle(lecturedto.getTitle());
			lecture.setCreatedBy(lecturedto.getCreatedBy());
			lecture.setUpdatedBy(lecturedto.getUpdatedBy());
			lecture.setSchedule(lecturedto.getSchedule());
			lecture.setConcludes(lecturedto.getConcludes());
			lecture.setZoomLink(lecturedto.getZoomLink());
			lecture.setType(lecturedto.getType());
			lecture.setBatch(lecturedto.getBatch());
			lecture.setCategory(lecturedto.getCategory());
			lecture.setSection(lecturedto.getSection());
			lecture.setTags(lecturedto.getTags());
			lecture.setVideo(lecture.getVideo());
			lecture.setNotes(lecturedto.getNotes());
			lecture.setOptional(lecturedto.getOptional());
			lecture.setHideVideo(lecturedto.isHideVideo());
			lecture.setWeek(lecturedto.getWeek());
			lecture.setDay(lecturedto.getDay());

			lectureDao.save(lecture);
			return "Lecture Updated Successfully";
		} else {
			throw new LectureException("There is no Lecture present!");
		}
	}

	@Override
	public LecturesPagination getAllLectures(Integer pageNumber, Integer pageSize) throws LectureException {

		PageRequest p = PageRequest.of(pageNumber, pageSize);
		Page<Lectures> allLecture =lectureDao.findAll(p);
		List<Lectures> lectures = allLecture.getContent();
	
		Map<Integer, Category> categoryMap = categoryserviceimpl.categoryMap();
		Map<Integer, Batch> batchMap = batchServiceimpl.batchMap();
		Map<Integer, Section> sectionMap = sectionServiceimpl.sectionMap();
		Map<Integer, LectureType> lectureTypeMap = lectureTypeimpl.lectureTypeMap();
		Map<Integer, User> userMap = userServiceimpl.userMap();

		List<LecturesResponseDto> response1 = new ArrayList<>();
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

				response1.add(lecturesResponseDto);
			}
		}
		LecturesPagination response = new LecturesPagination();
		response.setContent(response1);
		response.setPageSize(allLecture.getSize());
		response.setPageNumber(allLecture.getNumber());
		response.setTotalElements(allLecture.getTotalElements());
		response.setTotalPages(allLecture.getTotalPages());
		response.setLastPage(allLecture.isLast());
		return response;

	}

	@Override
	public Optional<Lectures> getLectureById(Integer id) throws LectureException {
		Optional<Lectures> lectureOptional = lectureDao.findById(id);
		if (lectureOptional.isPresent() && !lectureOptional.get().isDeleteStatus()) {
			return lectureOptional;

		} else {
			throw new LectureException("Lecture not found with ID: " + id);
		}

	}

	@Override
	public List<LecturesResponseDto> searchLectures(String title, Integer batch, Integer section, Integer type,
			Integer createdBy, String day, String week, LocalDate sdate, Integer category, Boolean optional)
			throws LectureException {

		if (title != null && title.equals("")) {
			title = null;
		}
		if (batch != null && batch.equals("")) {
			batch = null;
		}
		if (section != null && section.equals("")) {
			section = null;
		}
		if (type != null && type.equals("")) {
			type = null;
		}
		if (createdBy != null && createdBy.equals("")) {
			createdBy = null;
		}
		if (day != null && day.equals("")) {
			day = null;
		}
		if (week != null && week.equals("")) {
			week = null;
		}
		if (category != null && category.equals("")) {
			category = null;
		}
		List<Lectures> lectures = lectureDao.searchLectures(title, batch, section, type, createdBy, day, week, sdate,
				category, optional);
		Map<Integer, Category> categoryMap = categoryserviceimpl.categoryMap();
		Map<Integer, Batch> batchMap = batchServiceimpl.batchMap();
		Map<Integer, Section> sectionMap = sectionServiceimpl.sectionMap();
		Map<Integer, LectureType> lectureTypeMap = lectureTypeimpl.lectureTypeMap();
		Map<Integer, User> userMap = userServiceimpl.userMap();
		List<LecturesResponseDto> response = new ArrayList<>();
		for (Lectures lecture : lectures) {
			LocalDate sdate1 = lecture.getSchedule().toLocalDate();
			if (!lecture.isDeleteStatus() ) {
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
				if (lecture.getUpdatedBy() != null) {
					User user1 = userMap.get(lecture.getUpdatedBy());
					lecturesResponseDto.setCreatedBy(user1.getName());
				}
				lecturesResponseDto.setCreatedBy(user.getName());
				lecturesResponseDto.setWeek(lecture.getWeek());
				lecturesResponseDto.setType(type4.getType());
				lecturesResponseDto.setZoomLink(lecture.getZoomLink());
				lecturesResponseDto.setOptional(lecture.getOptional());
				response.add(lecturesResponseDto);
			}
		}
		return response;

	}

	

	@Override
	public String copyLecture(Integer lectureId, LectureDTO lecturedto) throws LectureException {
		Optional<Lectures> lectureOptional = lectureDao.findById(lectureId);
		if (lectureOptional.isPresent()) {
			Lectures oldlecture = lectureOptional.get();
			Lectures lecture = new Lectures();
			lecture.setTitle(lecturedto.getTitle());
			lecture.setCreatedBy(oldlecture.getCreatedBy());
			lecture.setSchedule(lecturedto.getSchedule());
			lecture.setConcludes(lecturedto.getConcludes());
			lecture.setZoomLink(lecturedto.getZoomLink());
			lecture.setType(oldlecture.getType());
			lecture.setBatch(lecturedto.getBatch());
			lecture.setCategory(oldlecture.getCategory());
			lecture.setSection(oldlecture.getSection());
			lecture.setTags(lecturedto.getTags());
			lecture.setNotes(lecturedto.getNotes());
			lecture.setOptional(oldlecture.getOptional());
			lecture.setHideVideo(oldlecture.isHideVideo());
			lecture.setWeek(lecturedto.getWeek());
			lecture.setDay(lecturedto.getDay());
			lecture.setUpdatedBy(lecturedto.getUpdatedBy());
			lecture.setCopyLectureFrom(lectureId);
			lectureDao.save(lecture);
			return "Copy Successful";
		} else {
			throw new LectureException("There is no Lecture present!");
		}

	}

	@Override
	public String addVideoToLecture(Integer lectureId, MultipartFile file) throws IOException, LectureException {
		Optional<Lectures> lectureOptional = lectureDao.findById(lectureId);
		Lectures lecture = lectureOptional.get();
		if (lectureOptional.isPresent()) {
			Video video = new Video();
			video.setData(file.getBytes());
			videoRepository.save(video);
			lecture.setVideo(video);
			lectureDao.save(lecture);
			return "Video added to the lecture successfully";
		} else {
			throw new LectureException("Lecture not found");
		}

	}

	@Override
	public byte[] getVideoForLecture(Integer lectureId) {
		Lectures lecture = lectureDao.findById(lectureId).orElse(null);
		Video video = videoRepository.findById(lecture.getVideo().getVideoId()).orElse(null);
		return video.getData();
	}

	@Override
	public List<LecturesResponseDto> getLecturesBySctionAndBatch(Integer section, Integer batch)
			throws LectureException {
		LocalDate date = LocalDate.now();
		List<Lectures> lectures = lectureDao.getLecturesBySctionAndBatch(section, batch, date);
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
				if (lecture.getUpdatedBy() != null) {
					User user1 = userMap.get(lecture.getUpdatedBy());
					lecturesResponseDto.setCreatedBy(user1.getName());
				}
				lecturesResponseDto.setCreatedBy(user.getName());
				lecturesResponseDto.setWeek(lecture.getWeek());
				lecturesResponseDto.setType(type4.getType());
				lecturesResponseDto.setZoomLink(lecture.getZoomLink());
				lecturesResponseDto.setOptional(lecture.getOptional());
				lecturesResponseDto.setVideo(lecture.getVideo());
				response.add(lecturesResponseDto);
			}
		}
		return response;

	}

	@Override
	public List<LecturesResponseDto> searchLecturesforstudent(String title, Integer batch, Integer section,
			Integer type, Integer createdBy, String day, String week, LocalDate sdate, Integer category,
			Boolean optional) throws LectureException {
		if (title != null && title.equals("")) {
			title = null;
		}
		if (batch != null && batch.equals("")) {
			batch = null;
		}
		if (section != null && section.equals("")) {
			section = null;
		}
		if (type != null && type.equals("")) {
			type = null;
		}
		if (createdBy != null && createdBy.equals("")) {
			createdBy = null;
		}
		if (day != null && day.equals("")) {
			day = null;
		}
		if (week != null && week.equals("")) {
			week = null;
		}
		if (category != null && category.equals("")) {
			category = null;
		}

		List<Lectures> lectures = lectureDao.searchLectures(title, batch, section, type, createdBy, day, week, sdate,
				category, optional);
		Map<Integer, Category> categoryMap = categoryserviceimpl.categoryMap();
		Map<Integer, Batch> batchMap = batchServiceimpl.batchMap();
		Map<Integer, Section> sectionMap = sectionServiceimpl.sectionMap();
		Map<Integer, LectureType> lectureTypeMap = lectureTypeimpl.lectureTypeMap();
		Map<Integer, User> userMap = userServiceimpl.userMap();
		LocalDate date = LocalDate.now();
		List<LecturesResponseDto> response = new ArrayList<>();
		for (Lectures lecture : lectures) {
			LocalDate sdate1 = lecture.getSchedule().toLocalDate();
			if (!lecture.isDeleteStatus() && (sdate1.isBefore(date) || sdate1.equals(date))) {
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
				if (lecture.getUpdatedBy() != null) {
					User user1 = userMap.get(lecture.getUpdatedBy());
					lecturesResponseDto.setCreatedBy(user1.getName());
				}
				lecturesResponseDto.setCreatedBy(user.getName());
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