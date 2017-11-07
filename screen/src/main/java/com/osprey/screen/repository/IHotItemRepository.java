package com.osprey.screen.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.osprey.screen.HotListItem;

public interface IHotItemRepository {

	List<HotListItem> findForDate(LocalDate dt);

	void deleteForDate(LocalDate dt);

	void deleteForDatesAndSymbol(String symbol, LocalDate earliestDate, LocalDate latestDate);

	void deleteAndPersist(String symbol, LocalDate earliestDate, LocalDate latestDate, List<? extends HotListItem> lst);

	void persist(List<? extends HotListItem> lst);

	int findCountBySymbolAndDays(String symbol, int days, LocalDate startDate);

	Map<String, Integer> findCountForModelBySymbolAndDays(String symbol, int days, LocalDate startDate);

}
