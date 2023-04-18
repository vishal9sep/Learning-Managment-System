package com.masai.team4.controller;

import com.masai.team4.dto.TypeDto;
import com.masai.team4.entities.LectureType;
import com.masai.team4.service.LectureTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class LectureTypeController {

    @Autowired
    private LectureTypeService lectureTypeService;


    //	save to db
    @PostMapping("/type")
    public ResponseEntity<LectureType> saveLectureType(@Valid @RequestBody LectureType typeDto){

        return new ResponseEntity<>(this.lectureTypeService.saveLectureType(typeDto), HttpStatus.CREATED );
    }



    @GetMapping("/typeList")
    public ResponseEntity<List<LectureType>> getLectureTypeList(){

        List<LectureType> typeDtos= this.lectureTypeService.getlist();

        return new ResponseEntity<>(typeDtos,HttpStatus.OK);

    }


}
