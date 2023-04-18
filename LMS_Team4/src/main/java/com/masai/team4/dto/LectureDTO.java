package com.masai.team4.dto;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Transient;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureDTO {
	private String title;
	private Integer category;
	private Integer batch;
	private Integer section;
	private Integer type;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private LocalDateTime schedule;
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private LocalDateTime concludes;
	@ElementCollection
	private List<String> tags;
	private Integer createdBy;
	private Integer updatedBy;
	private boolean hideVideo;
	private Boolean optional;
	private String zoomLink;
	private String day;
	private String week;
	private String notes;
	private Integer cpoyLectureFrom;
	

}
