package com.masai.team4.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureSearchDTO {
	private String title;
	private Integer category;
	private Integer batch;
	private Integer section;
	private Integer type;
	private Integer createdBy;
	private Boolean optional;
	private String day;
	private String week;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate startTime;

}
