package com.web;

import java.security.Principal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired 
	private UserUpdateFeedService feedService;
	
	@MessageMapping("/notifications") // so it will be /app/notifications 
	@SendToUser("/queue/notifications") // /topic/userupdates
	public Notification notifications(Principal username, Notification notification) {
		System.out.println("Got notification: " + notification);
		
		UserUpdate update = new UserUpdate();
		// FIXME: This should go into repository -> as save...:
		update.setId(-1L);
		update.setUser(username.getName());
		update.setMessage(notification.getText());
		update.setTimestamp(LocalDateTime.now());
		// ----------------------------------------------------
		
		feedService.broadcastUserUpdate(update);
		
		return new Notification("Saved UserUpdate for user: " + update.getUser() + ";");
	}
}
