package com.osprey.screen.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.BlackListItem;
import com.osprey.screen.repository.IOspreyJSONObjectRepository;

public class BlackListService {

	private static final String BLACK_LIST_KEY = "black-list";

	private ObjectMapper om;
	private IOspreyJSONObjectRepository jsonRepository;

	public BlackListService(ObjectMapper om, IOspreyJSONObjectRepository jsonRepository) {
		this.om = om;
		this.jsonRepository = jsonRepository;
	}

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
