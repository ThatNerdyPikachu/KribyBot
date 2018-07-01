package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.Iterator;

import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandListSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.commands.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.commands.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		Iterator<AudioSwitch> it = AudioCommand.switches.iterator();
		StringBuilder builder = new StringBuilder();
		builder.append("```");
		while (it.hasNext()) {
			AudioSwitch ls = it.next();
			builder.append(ls.getSwitch());
			builder.append('\n');
		}
		builder.append("```");
		sendMessage(builder.toString(), event.getChannel());
	}

}
