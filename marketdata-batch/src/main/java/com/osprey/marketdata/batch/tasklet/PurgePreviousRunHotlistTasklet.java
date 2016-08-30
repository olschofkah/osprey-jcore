package com.osprey.marketdata.batch.tasklet;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.osprey.screen.repository.IHotShitRepository;

public class PurgePreviousRunHotlistTasklet implements Tasklet {

	private final static Logger logger = LogManager.getLogger(PurgePreviousRunHotlistTasklet.class);
	
	private IHotShitRepository repo;
	
	public PurgePreviousRunHotlistTasklet(IHotShitRepository repo){
		this.repo=repo;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		LocalDate today = LocalDate.now();
		
		logger.info("Removing existing hotlist items for {}.", () -> today);
		
		repo.deleteHotShitForDate(today);
		
		return RepeatStatus.FINISHED;
	}

}
