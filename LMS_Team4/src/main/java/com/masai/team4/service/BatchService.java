package com.masai.team4.service;

import java.util.List;

import com.masai.team4.entities.Batch;


public interface BatchService {
	public Batch registerBatch( Batch batchdata);
	public List<Batch> getBatchList();
	public String deleteBatchById(Integer batchId);
}
