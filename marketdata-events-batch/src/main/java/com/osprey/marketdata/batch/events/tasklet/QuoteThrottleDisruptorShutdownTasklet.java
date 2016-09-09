package com.osprey.marketdata.batch.events.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.osprey.marketdata.throttle.ThrottleDisruptor;



public class QuoteThrottleDisruptorShutdownTasklet implements Tasklet {

	private ThrottleDisruptor throttleDisruptor;

	public QuoteThrottleDisruptorShutdownTasklet(ThrottleDisruptor throttleDisruptor) {
		this.throttleDisruptor = throttleDisruptor;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		throttleDisruptor.stop();
		return RepeatStatus.FINISHED;
	}

}
