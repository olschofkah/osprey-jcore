package com.osprey.marketdata.batch.processor.lmax;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class ThrottleDisruptor {

	final static Logger logger = LogManager.getLogger(ThrottleEventHandler.class);

	private final AtomicLong throttleCapacity;
	private final AtomicBoolean running;
	private final ThreadFactory threadFactory;

	private int ringBufferSize = 32;

	private Disruptor<ThrottleEvent> disruptor;
	private ThrottleEventProducer producer;

	public ThrottleDisruptor(AtomicLong capacity, ThreadFactory threadFactory) {
		this.throttleCapacity = capacity;
		this.threadFactory = threadFactory;
		running = new AtomicBoolean(false);
	}

	@PostConstruct
	private void init() {

		EventFactory<ThrottleEvent> factory = new EventFactory<ThrottleEvent>() {

			@Override
			public ThrottleEvent newInstance() {
				return new ThrottleEvent();
			}

		};

		disruptor = new Disruptor<>(factory, ringBufferSize, threadFactory);

		// Connect the handler
		disruptor.handleEventsWith(new ThrottleEventHandler(throttleCapacity));

		// Start the Disruptor, starts all threads running
		disruptor.start();

		// Get the ring buffer from the Disruptor to be used for publishing.
		RingBuffer<ThrottleEvent> ringBuffer = disruptor.getRingBuffer();

		producer = new ThrottleEventProducer(ringBuffer);
	}

	public void stop() {
		running.lazySet(false);
	}

	public void start() {
		if (running.compareAndSet(false, true)) {
			threadFactory.newThread(new Runnable() {

				@Override
				public void run() {
					while (running.get()) {
						producer.load();
					}
					disruptor.shutdown();
				}

			}).start();
		}
	}
}
