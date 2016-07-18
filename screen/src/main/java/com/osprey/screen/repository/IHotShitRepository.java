package com.osprey.screen.repository;

import java.time.LocalDate;
import java.util.List;

import com.osprey.screen.HotListItem;

public interface IHotShitRepository {

	List<HotListItem> findForDate(LocalDate dt);

	void deleteHotShitForDate(LocalDate dt);

	void persistThaHotShit(List<? extends HotListItem> lst);
}
