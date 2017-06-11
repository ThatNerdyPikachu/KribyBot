package com.shadowninja108.bot.command.communication;

import static com.shadowninja108.util.MessageUtil.*;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SayDiscreteCommand implements Command {

	@Override
	public String getName() {
		return Translatable.get("sayd.prefix");
	}

	@Override
	public String getDescription() {
		return Translatable.get("sayd.description");
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		deleteMessage(event.getMessage());
		String message = event.getMessage().getRawContent();
		int i = message.indexOf(" ");
		if (i > 0) {
			message = message.substring(i + 1);

			sendMessage(message, event.getChannel());
		}
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		sendMessage(Translatable.get("sayd.private_rejection"), event.getAuthor());
	}

}
