package com.shadowninja108.bot.shell.util;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ExceptionTest extends ShellCommand {

	@Override
	public String getPrefix() {
		return "exception";
	}

	@Override
	public String getDescription() {
		return "Causes a test exception in the bot.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 0 };
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		throw new NullPointerException("Test exception!");
	}

	@Override
	public boolean meetsRequirements(selected selected) {
		return true;
	}

}
