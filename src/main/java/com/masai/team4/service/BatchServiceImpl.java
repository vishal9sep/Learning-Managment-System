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
import com.masai.team4.repository.BatchRepo;



@Service
public class BatchServiceImpl implements BatchService{
	@Autowired
	BatchRepo batchrepo;

	@Override
	public Batch registerBatch(Batch batchdata) {
		return batchrepo.save(batchdata);
	}

	@Override
	public List<Batch> getBatchList() {
		List<Batch> batch=batchrepo.batchList();
		if(batch.size()>0) {
		return batch;
		}else {
			return null;
		}
				
	}
	
	@Override
	@Transactional
	public String deleteBatchById(Integer batchId){

		Optional<Batch> batch = batchrepo.findById(batchId);
		if(batch.isPresent()) {
			batch.get().setStatus('D');
			return "Delete Done";
		}else {
			return "Section does not exists or already deleted";
		}

	}
	
	public Map<Integer, Batch> batchMap() {
			
	    Map<Integer, Batch> map = new HashMap<>();
	    batchrepo.findAll().forEach(c -> map.put(c.getBatchId(), c));
	    return map;
	}
	
}
