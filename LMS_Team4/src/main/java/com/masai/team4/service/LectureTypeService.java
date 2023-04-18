package com.masai.team4.service;

import java.util.List;

import com.masai.team4.dto.TypeDto;
import com.masai.team4.entities.LectureType;

public interface LectureTypeService {

    LectureType saveLectureType(LectureType typeDto);

    List<LectureType> getlist();


}
