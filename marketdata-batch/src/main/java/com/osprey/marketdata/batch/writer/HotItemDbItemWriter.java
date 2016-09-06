package com.osprey.marketdata.batch.writer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import com.osprey.screen.HotListItem;
import com.osprey.screen.repository.IHotItemRepository;

public class HotItemDbItemWriter implements ItemWriter<HotListItem> {

	private final static Logger logger = LogManager.getLogger(HotItemDbItemWriter.class);

	private IHotItemRepository repo;

	public HotItemDbItemWriter(IHotItemRepository repo) {
		this.repo = repo;
	}

	@Override
	public void write(final List<? extends HotListItem> items) throws Exception {

		logger.info("Persisting {} hotlist items ... ", () -> items.size());

		if (items.isEmpty()) {
			return;
		}

		HotListItem itemZero = items.get(0);
		LocalDate lDate = itemZero.getReportDate().toLocalDate();

		for (HotListItem item : items) {
			item.setRecentCount(repo.findCountBySymbolAndDays(item.getKey().getSymbol(), 10, lDate));
			Map<String, Integer> modelStatMap = repo.findCountForModelBySymbolAndDays(item.getKey().getSymbol(), 10, lDate);
			item.addModelCounts(modelStatMap);
		}

		repo.deleteAndPersist(itemZero.getKey().getSymbol(), lDate, lDate, items);
	}

}
