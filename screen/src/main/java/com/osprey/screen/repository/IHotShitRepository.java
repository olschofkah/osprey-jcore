package com.osprey.screen.repository;

import java.time.LocalDate;
import java.util.List;

import com.osprey.screen.ScreenSuccessSecurity;

public interface IHotShitRepository {

	List<ScreenSuccessSecurity> findForDate(LocalDate dt);

	void deleteHotShitForDate(LocalDate dt);

	void persistThaHotShit(List<? extends ScreenSuccessSecurity> lst);
}
