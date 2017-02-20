package com.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MarcoController {
	
	private static final Logger logger = LoggerFactory.getLogger(MarcoController.class);
	
	@MessageMapping("/marco") // so it will be /app/marco I guess...
	public Shout shoutMessage(Shout shout) {
		logger.info("Received: " + shout.getMsg());
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) { }
		
		return new Shout("POLO");
	}
}
