package com.shadowninja108.bot.command.settings;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.util.MessageUtil;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RefreshCommand implements Command {
	
	@Override
	public String getName() {
		return "refreshSettings";
	}

	@Override
	public String getDescription() {
		return "Pulls latest settings and loads them in.";
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		MessageUtil.sendMessage(CommandProcessor.getGuildData(event.getGuild()).settings.refresh(), event.getChannel());
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		MessageUtil.sendMessage("No settings here!", event.getChannel());
	}

}
