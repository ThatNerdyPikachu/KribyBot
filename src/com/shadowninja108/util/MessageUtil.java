package com.shadowninja108.util;

import java.io.InputStream;

import com.shadowninja108.main.Launcher;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.AttachmentOption;

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
		RestAction<Message> action = channel.sendFile(in, fileName, (AttachmentOption) null);
		action.queue();
	}
	
	public static void sendFile(InputStream in, String fileName, PrivateChannel channel) {
		RestAction<Message> action = channel.sendFile(in, fileName, (AttachmentOption) null);
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
