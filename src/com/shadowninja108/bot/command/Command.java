package com.shadowninja108.bot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract interface Command {

	public abstract String getName();

	public abstract String getDescription();

	public void serverMessage(MessageReceivedEvent event);

	public void privateMessage(MessageReceivedEvent event);
}