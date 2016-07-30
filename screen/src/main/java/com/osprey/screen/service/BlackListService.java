package com.osprey.screen.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.BlackListItem;
import com.osprey.screen.repository.jdbctemplate.OspreyJSONObjectJdbcRepository;

public class BlackListService {

	private static final String BLACK_LIST_KEY = "black-list";

	@Autowired
	@Qualifier("om1")
	private ObjectMapper om;
	@Autowired
	private OspreyJSONObjectJdbcRepository jsonRepository;

	public List<BlackListItem> findBlackListSymbols() {
		List<BlackListItem> blackList = Collections.emptyList();

		String obj = jsonRepository.find(BLACK_LIST_KEY);

		try {
			blackList = om.readValue(obj, new TypeReference<List<BlackListItem>>() {
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return blackList;
	}

}
