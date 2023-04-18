package com.masai.team4.entities;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lectures {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer lectureId;
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
    @OneToOne
    @JoinColumn(name = "video_Id")
	private Video video;
    private boolean deleteStatus=false;
    private Integer copyLectureFrom;

}
