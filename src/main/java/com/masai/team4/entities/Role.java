package com.masai.team4.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;


}
