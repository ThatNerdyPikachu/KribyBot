package com.shadowninja108.bot.command.settings;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.util.MessageUtil;
import com.shadowninja108.util.setting.SettingsInstance;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PrintSettings implements Command {

	@Override
	public String getName() {
		return "printSettings";
	}

	@Override
	public String getDescription() {
		return "Prints out currently stored settings for guild.";
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		SettingsInstance instance = CommandProcessor.getGuildData(event.getGuild()).settings;
		StringBuilder builder = new StringBuilder();
		instance.getSettings().forEach((k,v) -> {
			builder.append(k);
			builder.append('=');
			builder.append(v);
			builder.append('\n');
		});
		MessageUtil.sendMessage(builder.toString(), event.getChannel());
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		MessageUtil.sendMessage("No settings here!", event.getChannel());
	}

}
