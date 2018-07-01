package com.shadowninja108.bot.command.util;

import com.shadowninja108.bot.command.Command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ExceptionTest implements Command {

	@Override
	public String getName() {
		return "exception";
	}

	@Override
	public String getDescription() {
		return "Throws a test exception.";
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		throw new RuntimeException("Test!");
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		serverMessage(event);		
	}

}
