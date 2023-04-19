package com.masai.team4.controller;
import com.masai.team4.entities.Lectures;
import com.masai.team4.exception.LectureException;
import com.masai.team4.service.CsvLectureService;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LectureByCSVController {

    @Autowired
    private CsvLectureService byCSVservice;

    @PostMapping("/upload/csvLectures")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException, LectureException, CsvException {


        String lectureByCSVS = this.byCSVservice.registerNewLecture(file);

        return new ResponseEntity<>("lecture added successfully! ", HttpStatus.CREATED);

    }
}
