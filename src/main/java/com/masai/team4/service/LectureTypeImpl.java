package com.masai.team4.service;

import com.masai.team4.dto.TypeDto;
import com.masai.team4.entities.LectureType;
import com.masai.team4.repository.LectureTypeRepo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

@Service
public class LectureTypeImpl implements LectureTypeService{

    @Autowired
    private LectureTypeRepo lectureTypeRepo;

    @Autowired
    private ModelMapper modelMapper;
    
    private static List<LectureType> lectureTypeList;
    
	@PostConstruct
	void init() {
		lectureTypeList = lectureTypeRepo.findAll();

	   }

    
    public Map<Integer, LectureType> lectureTypeMap() {

    	Map<Integer, LectureType> map = new HashMap<>();
    	lectureTypeList.forEach(c -> map.put(c.getId(), c));
    	return map;
	}



    @Override
    public LectureType saveLectureType(LectureType typeDto) {

        return this.lectureTypeRepo.save(typeDto);

    }


    @Override
    public List<LectureType> getlist() {

        List<LectureType> lectureTypes=this.lectureTypeRepo.findAll();
        return lectureTypes;
    }
}