package com.masai.team4.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Bookmarks")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bookmarks {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer userId;

	private Integer lectureId;
	private boolean status;
	

	
}
