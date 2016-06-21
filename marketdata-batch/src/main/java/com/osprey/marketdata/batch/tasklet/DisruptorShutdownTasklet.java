package com.osprey.marketdata.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.marketdata.batch.processor.lmax.ThrottleDisruptor;

public class DisruptorShutdownTasklet implements Tasklet{
	
	@Autowired
	private ThrottleDisruptor throttleDisruptor;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		throttleDisruptor.stop();
		return RepeatStatus.FINISHED;
	}

}
