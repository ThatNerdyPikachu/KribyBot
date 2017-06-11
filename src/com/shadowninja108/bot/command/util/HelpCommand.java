package com.shadowninja108.bot.command.util;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.Iterator;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.main.Launcher;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand implements Command {

	@Override
	public String getName() {
		return Translatable.get("help.prefix");
	}

	@Override
	public String getDescription() {
		return Translatable.get("help.description");
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		StringBuilder builder = new StringBuilder();
		builder.append("```");
		Iterator<Command> it = CommandProcessor.commands.iterator();
		while (it.hasNext()) {
			Command ls = it.next();
			builder.append(Launcher.getPrefix());
			builder.append(ls.getName());
			builder.append(" - ");
			builder.append(ls.getDescription());
			builder.append('\n');
		}
		builder.append("```");
		sendMessage(Translatable.get("help.response"), event.getChannel());
		sendMessage(builder.toString(), event.getAuthor());
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		StringBuilder builder = new StringBuilder();
		builder.append("```");
		Iterator<Command> it = CommandProcessor.commands.iterator();
		while (it.hasNext()) {
			Command ls = it.next();
			builder.append(Launcher.getPrefix());
			builder.append(ls.getName());
			builder.append(" - ");
			builder.append(ls.getDescription());
			builder.append('\n');
		}
		builder.append("```");
		sendMessage(builder.toString(), event.getAuthor());
	}

}
