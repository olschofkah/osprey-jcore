package com.osprey.marketdata.batch.writer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.screen.HotListItem;
import com.osprey.screen.repository.IHotShitRepository;

public class HotShitDbItemWriter implements ItemWriter<HotListItem> {

	final static Logger logger = LogManager.getLogger(HotShitDbItemWriter.class);
	
	@Autowired
	private IHotShitRepository repo;

	@Override
	public void write(final List<? extends HotListItem> items) throws Exception {

		logger.info("Persisting {} hotlist items ... ", () -> items.size());

		if (items.isEmpty()) {
			return;
		}
		
		repo.persistThaHotShit(items);

	}

}
