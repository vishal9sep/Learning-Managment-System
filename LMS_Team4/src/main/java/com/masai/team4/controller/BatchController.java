package com.masai.team4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.team4.entities.Batch;
import com.masai.team4.exception.LectureException;
import com.masai.team4.service.BatchService;

@RestController
@RequestMapping("/api")
public class BatchController {
	@Autowired
	private BatchService batchService;

	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addbatch")
	public ResponseEntity<String> registerBatchHandler(@RequestBody Batch batch) {

		Batch addbatch = batchService.registerBatch(batch);

		return ResponseEntity.ok("success");
	}

	
	@GetMapping("/batchList")
	public ResponseEntity<List<Batch>> getBatchList() {
		
		return new ResponseEntity<List<Batch>>(batchService.getBatchList(), HttpStatus.OK);
		
	}
	@PostMapping("/removeBatch/{batchId}")
	public ResponseEntity<Map<String, String>> deleteLectureHandler(@PathVariable("batchId") Integer batchId)
			throws LectureException {

		String message = batchService.deleteBatchById(batchId);

		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
}
