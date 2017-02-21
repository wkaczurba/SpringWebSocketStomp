package com.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@MessageMapping("/notifications") // so it will be /app/notifications 
	public Notification notifications(Notification notification) {
		System.out.println("Got notification: " + notification);
		return new Notification(notification.getText());
	}
}
