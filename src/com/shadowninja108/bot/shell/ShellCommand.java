package com.shadowninja108.bot.shell;

import com.shadowninja108.bot.shell.Selected.selected;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class ShellCommand {
	public abstract String getPrefix();

	public abstract String getDescription();

	public abstract Byte[] getParameterCount();

	public abstract void execute(String[] args, Selected selected, MessageReceivedEvent event);

	public abstract boolean meetsRequirements(selected selected);
}
