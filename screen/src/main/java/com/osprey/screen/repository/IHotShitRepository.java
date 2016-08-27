package com.osprey.screen.repository;

import java.time.LocalDate;
import java.util.List;

import com.osprey.screen.HotListItem;

public interface IHotShitRepository {

	List<HotListItem> findForDate(LocalDate dt);

	void deleteHotShitForDate(LocalDate dt);

	void deleteHotShitForDatesAndSymbol(String symbol, LocalDate earliestDate, LocalDate latestDate);

	void deleteAndPersist(String symbol, LocalDate earliestDate, LocalDate latestDate, List<? extends HotListItem> lst);

	void persistThaHotShit(List<? extends HotListItem> lst);

	int findCountBySymbolAndDays(String symbol, int days);

}
