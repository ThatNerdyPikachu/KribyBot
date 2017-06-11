package com.shadowninja108.bot.command.communication;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static com.shadowninja108.util.MessageUtil.*;

public class SayCommand implements Command {

	@Override
	public String getName() {
		return Translatable.get("say.prefix");
	}

	@Override
	public String getDescription() {
		return Translatable.get("say.description");
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		String message = event.getMessage().getRawContent();
		int i = message.indexOf(" ");
		if (i > 0) {
			message = message.substring(i + 1);

			sendMessage(message, event.getChannel());
		}
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		String message = event.getMessage().getContent();
		int i = message.indexOf(" ");
		if (i > 0) {
			message = message.substring(i + 1);

			sendMessage(message, event.getAuthor());
		}
	}

}
