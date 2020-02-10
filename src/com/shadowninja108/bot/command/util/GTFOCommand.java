package com.shadowninja108.bot.command.util;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GTFOCommand implements Command {

	@Override
	public String getName() {
		return Translatable.get("gtfo.prefix");
	}

	@Override
	public String getDescription() {
		return Translatable.get("gtfo.description");
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		sendMessage(Translatable.get("gtfo.leave_message"), event.getChannel());
		event.getJDA().shutdown();
		System.exit(0);
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		sendMessage(Translatable.get("gtfo.private_rejection"), event.getChannel());
	}

}
