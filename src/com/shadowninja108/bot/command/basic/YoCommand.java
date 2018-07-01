package com.shadowninja108.bot.command.basic;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class YoCommand implements Command {

	@Override
	public String getName() {
		return Translatable.get("yo.prefix");
	}

	@Override
	public String getDescription() {
		return Translatable.get("yo.description");
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		sendMessage(Translatable.get("yo.response_prefix") + " " + event.getAuthor().getAsMention(), event.getChannel());
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		serverMessage(event);
	}

}
