package com.masai.team4.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name="section")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer sectionId;

	private String section;
	private char Status='A';


}
