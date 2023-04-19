package com.masai.team4.service;

import com.masai.team4.dto.LectureDTO;
import com.masai.team4.exception.LectureException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsvLectureService {

    public String registerNewLecture(MultipartFile multipartFile) throws LectureException, IOException;
}
