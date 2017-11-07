package com.osprey.marketdata.batch.processor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.BlackListItem;
import com.osprey.screen.ScreenCriteriaGenerator;
import com.osprey.screen.ScreenPlan;
import com.osprey.screen.ScreenPlanFactory;
import com.osprey.screen.ScreenStrategyEntry;
import com.osprey.screen.SimpleScreenExecutor;
import com.osprey.screen.criteria.IScreenCriteria;
import com.osprey.screen.criteria.SymbolCriteria;
import com.osprey.screen.criteria.constants.ScreenType;
import com.osprey.screen.service.BlackListService;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class InitialScreenService {

	private final static Logger logger = LogManager.getLogger(InitialScreenService.class);

	@Value("${initial.screen.set.json}")
	private String screenJsonFile;

	private BlackListService bls;
	private ObjectMapper om;

	public InitialScreenService(BlackListService bls, ObjectMapper om) {
		this.bls = bls;
		this.om = om;
	}

	private List<IScreenCriteria> criteria;

	@PostConstruct
	public void init() {
		InputStream in = getClass().getClassLoader().getResourceAsStream(screenJsonFile);

		ScreenStrategyEntry entry = null;
		try {
			entry = om.readValue(in, ScreenStrategyEntry.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Convert the criteria generators into criteria.
		IScreenCriteria sc;
		criteria = new ArrayList<>(entry.getScreenCriteria().size());
		for (ScreenCriteriaGenerator generator : entry.getScreenCriteria()) {
			sc = generator.generate();
			criteria.add(sc);

			// look for the black list to update the symbols from the db ...
			if (sc.getType() == ScreenType.SYMBOL && !((SymbolCriteria) sc).isContains()) {
				List<BlackListItem> blackList = bls.findBlackListSymbols();
				Set<String> blackListSymbolStrings = new HashSet<>(blackList.size());

				for (BlackListItem bli : blackList) {
					blackListSymbolStrings.add(bli.getSymbol());
				}

				logger.info("Setting the black list symbols to {}", () -> blackListSymbolStrings);
				((SymbolCriteria) sc).setSymbols(blackListSymbolStrings);
			}
		}
	}

	public List<IScreenCriteria> getCriteria() {
		return Collections.unmodifiableList(criteria);
	}

	public Set<SecurityKey> filter(Set<SecurityQuoteContainer> securities) {
		ScreenPlanFactory screenPlanFactory = new ScreenPlanFactory(securities);
		List<ScreenPlan> screenPlan = screenPlanFactory.build(criteria);

		SimpleScreenExecutor executor = new SimpleScreenExecutor();
		executor.setPlans(screenPlan);
		executor.execute();

		return executor.getResultSet();
	}
}
