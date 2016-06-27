package com.osprey.integration.slack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SlackClient {

	final static Logger logger = LogManager.getLogger(SlackClient.class);

	@Value("${slack.api.chat.url}")
	private String slackApiUrl;

	@Value("${slack.api.user}")
	private String user;

	@Value("${slack.api.emoji}")
	private String emoji;

	@Autowired
	private RestTemplate http;

	@Autowired
	private ObjectMapper mapper;

	public void postMessage(Object message) {

		String payload = null;
		try {
			payload = mapper.writeValueAsString(new SlackRequest(user, message, emoji));
		} catch (RestClientException | JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		final String finalPayload = payload;

		ResponseEntity<String> response = http.postForEntity(slackApiUrl, finalPayload, String.class);
		
		if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
			logger.info("Successfully notified slack of message {}", () -> finalPayload);
		} else {
			logger.warn("Failed to notify slack of message {}", () -> finalPayload);
		}

	}

	public class SlackRequest {

		private String username;
		private Object text;
		private String icon_emoji;

		public SlackRequest(String user, Object text, String emoji) {
			this.setUsername(user);
			this.text = text;
			this.icon_emoji = emoji;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Object getText() {
			return text;
		}

		public void setText(Object text) {
			this.text = text;
		}

		public String getIcon_emoji() {
			return icon_emoji;
		}

		public void setIcon_emoji(String icon_emoji) {
			this.icon_emoji = icon_emoji;
		}

	}

}
