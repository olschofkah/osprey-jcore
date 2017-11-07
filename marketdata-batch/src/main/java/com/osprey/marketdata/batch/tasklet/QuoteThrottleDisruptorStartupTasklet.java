package com.osprey.marketdata.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.osprey.marketdata.batch.processor.lmax.ThrottleDisruptor;

public class QuoteThrottleDisruptorStartupTasklet implements Tasklet {

	private ThrottleDisruptor throttleDisruptor;

	public QuoteThrottleDisruptorStartupTasklet(ThrottleDisruptor throttleDisruptor) {
		this.throttleDisruptor = throttleDisruptor;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		throttleDisruptor.start();
		return RepeatStatus.FINISHED;
	}

}
