package com.web;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserUpdateFeedServiceImpl implements UserUpdateFeedService {
	
	private SimpMessagingTemplate messagingTemplate;
	final Pattern pattern = Pattern.compile("\\@(\\S+)");
	
	@Autowired
	public UserUpdateFeedServiceImpl(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public void broadcastUserUpdate(UserUpdate update) {
		System.out.println("SENDING BROADCAST: " + update);
		
		// 
		messagingTemplate.convertAndSend("/topic/userupdates", update );
		
		Matcher matcher = pattern.matcher(update.getMessage());
		if (matcher.find()) {
			String user = matcher.group(1);
			System.out.println("  - FOUND USER: " + user);
			messagingTemplate.convertAndSendToUser(user, "/queue/notifications", 
					new Notification("You ("+user+") have got mentioned!"));
		} else {
			System.out.println("  - NOT FOUND ANY USERS...");
		}
	}

}
