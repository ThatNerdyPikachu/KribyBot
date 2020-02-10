package com.shadowninja108.bot.command.util;

import static com.shadowninja108.util.MessageUtil.*;

import java.util.Iterator;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.main.Launcher;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
		EmbedBuilder embedBuilder = new EmbedBuilder();
		Iterator<Command> it = CommandProcessor.commands.iterator();
		String prefix = Launcher.getPrefix();
		while (it.hasNext()) {
			Command ls = it.next();
			embedBuilder.addField(prefix + ls.getName(), ls.getDescription(), false);
		}
		if (!event.isFromType(ChannelType.PRIVATE))
			sendMessage(Translatable.get("help.response"), event.getChannel());
		sendEmbed(embedBuilder.build(), event.getAuthor());
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		serverMessage(event);
	}

}
