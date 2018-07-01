package com.shadowninja108.util;

import java.io.InputStream;

import com.shadowninja108.main.Launcher;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.RestAction;

public class MessageUtil {
	public static void sendMessage(String message, MessageChannel channel) {
		try {
			if (message.length() > 2000) {
				sendMessage(Translatable.get("global.message_overflow") + " " + Launcher.author_mentionable, channel);
			} else {
				RestAction<Message> action = channel.sendMessage(message);
				action.queue();
			}
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendMessage(String message, User user) {
		RestAction<Message> action = user.openPrivateChannel().complete().sendMessage(message);
		action.queue();
	}

	public static void deleteMessage(Message msg) {
		RestAction<Void> action = msg.delete();
		action.complete();
	}

	public static void sendFile(InputStream in, String fileName, MessageChannel channel) {
		RestAction<Message> action = channel.sendFile(in, fileName, null);
		action.queue();
	}
	
	public static void sendFile(InputStream in, String fileName, PrivateChannel channel) {
		RestAction<Message> action = channel.sendFile(in, fileName, null);
		action.queue();
	}
	public static void sendEmbed(MessageEmbed embed , MessageChannel channel) {
		RestAction<Message> action = channel.sendMessage(embed);
		action.queue();
	}
	public static void sendEmbed(MessageEmbed embed , User user) {
		RestAction<Message> action = user.openPrivateChannel().complete().sendMessage(embed);
		action.queue();
	}
}
