package com.masai.team4.service;

import java.util.List;

import com.masai.team4.entities.Section;

public interface SectionService {
	public Section registerSection( Section sectiondata);
	public List<Section> getSectionList();
	public String deleteSectionById(Integer sectionId);
}
