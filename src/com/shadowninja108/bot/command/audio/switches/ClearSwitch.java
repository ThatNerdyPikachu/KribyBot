package com.shadowninja108.bot.command.audio.switches;

import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ClearSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.clear.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.clear.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args, AudioCommand command) {
		GuildMusicManager manager = command.getManager(event.getGuild());
		manager.scheduler.clear();
	}

}
