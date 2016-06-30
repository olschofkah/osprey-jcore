package com.osprey.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.integration.slack.SlackClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IntegrationApplication.class)
public class SlackTest {

	@Autowired
	private SlackClient slack;
	
	@Test
	public void slackMessageTest1() throws Exception{
		slack.postMessage("How can I help master.");
	}
	
}
