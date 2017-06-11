package com.shadowninja108.util;

import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.RestAction;

public class MessageUtil {
	public static void sendMessage(String message, MessageChannel channel) {
		if (message.length() > 2000) {
			sendMessage(Translatable.get("global.message_overflow"), channel);
		} else {
			RestAction<Message> action = channel.sendMessage(message);
			action.complete();
		}
	}

	public static void sendMessage(String message, User user) {
		RestAction<Message> action = user.openPrivateChannel().complete().sendMessage(message);
		action.complete();
	}

	public static void deleteMessage(Message msg) {
		RestAction<Void> action = msg.delete();
		action.complete();
	}
}
