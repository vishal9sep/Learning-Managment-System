package com.masai.team4.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.team4.entities.Batch;
import com.masai.team4.entities.Lectures;
import com.masai.team4.entities.Section;
import com.masai.team4.exception.LectureException;
import com.masai.team4.repository.SectionRepo;
@Service
public class SectionServiceImpl implements SectionService{
	@Autowired
	SectionRepo sectionrepo;
	
 
	@Override
	public Section registerSection( Section sectiondata) {
		return sectionrepo.save(sectiondata);
	}

	@Override
	public List<Section> getSectionList() {
		List<Section> section=sectionrepo.sectionList();
		if(section.size()>0) {
		return section;
		}else {
			return null;
		}
				
	}
	@Override
	@Transactional
	public String deleteSectionById(Integer sectionId) {

		Optional<Section> section = sectionrepo.findById(sectionId);
		if(section.isPresent()) {
			section.get().setStatus('D');
			return "Delete Done";
		}else {
			return "Section does not exists or already deleted";
		}
		
		

	}
	
	 public Map<Integer, Section> sectionMap() {
			
	    	Map<Integer, Section> map = new HashMap<>();
	    	sectionrepo.findAll().forEach(c -> map.put(c.getSectionId(), c));
	    	return map;
		}

}
