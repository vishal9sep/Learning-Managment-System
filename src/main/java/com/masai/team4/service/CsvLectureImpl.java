package com.masai.team4.service;

import com.masai.team4.dto.LectureDTO;
import com.masai.team4.entities.Lectures;
import com.masai.team4.exception.LectureException;
import com.masai.team4.repository.LecturesRepo;
import com.opencsv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvLectureImpl implements CsvLectureService{

    @Autowired
    private LecturesRepo lectureDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String registerNewLecture(MultipartFile multipartFile) throws LectureException, IOException {

        Reader reader = new InputStreamReader(multipartFile.getInputStream());

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader();
//        CSVParser csvParser = new CSVParser();
//        CSVParser csvParser = new CSVParser(reader, csvFormat);

        CSVParser csvParser= new CSVParser();

        List<LectureDTO> lectureDTOS = new ArrayList<>();

        for (CSVRecord csvRecord : csvFormat.parse(reader)) {
            LectureDTO dto = new LectureDTO();

            dto.setTitle(csvRecord.get("title"));
            dto.setCategory(Integer.parseInt(csvRecord.get("category")));
            dto.setBatch(Integer.parseInt(csvRecord.get("batch")));
            dto.setSection(Integer.parseInt(csvRecord.get("section")));
            dto.setType(Integer.parseInt(csvRecord.get("type")));
            dto.setSchedule(LocalDateTime.parse(csvRecord.get("schedule_date")));
            dto.setConcludes(LocalDateTime.parse(csvRecord.get("concludes_date")));
            dto.setTags(Arrays.asList(csvRecord.get("tags").split(",")));
            dto.setCreatedBy(Integer.parseInt(csvRecord.get("created_by")));
            dto.setHideVideo(Boolean.parseBoolean(csvRecord.get("hide_video")));
            dto.setZoomLink(csvRecord.get("zoom_link"));
            dto.setDay(csvRecord.get("day"));
            dto.setWeek(csvRecord.get("week"));
            dto.setNotes(csvRecord.get("notes"));
            dto.setOptional(Boolean.parseBoolean(csvRecord.get("optional")));

            lectureDTOS.add(dto);
        }
//        csvParser.close();

//        List<Lectures> lectures = modelMapper.map(lectureDTOS, Lectures.class);
        List<Lectures> lecturesList = lectureDTOS.stream().map((lecture) -> this.modelMapper.map(lecture, Lectures.class))
                .collect(Collectors.toList());

        lectureDao.saveAll(lecturesList);


        return "lectures saved!"; // or whatever view you want to return

    }
}
