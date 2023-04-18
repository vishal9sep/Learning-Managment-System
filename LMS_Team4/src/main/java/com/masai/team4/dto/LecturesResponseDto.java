package com.masai.team4.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.ElementCollection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.masai.team4.entities.Video;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LecturesResponseDto {
	private Integer lectureId;
	private String title;
	private String category;
	private String batch;
	private String section;
	private String type;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime schedule;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime concludes;

	@ElementCollection
	private List<String> tags;
	private String createdBy;
	private String updatedBy;
	private boolean hideVideo;
	private boolean optional;
	private String zoomLink;
	private String day;
	private String week;
	private String notes;
	private Video video;
	private Integer copyLectureFrom;
}
